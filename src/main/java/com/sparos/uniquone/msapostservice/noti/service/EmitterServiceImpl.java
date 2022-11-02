package com.sparos.uniquone.msapostservice.noti.service;

import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import com.sparos.uniquone.msapostservice.cool.domain.Cool;
import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.follow.domain.Follow;
import com.sparos.uniquone.msapostservice.noti.domain.Noti;
import com.sparos.uniquone.msapostservice.noti.domain.NotiType;
import com.sparos.uniquone.msapostservice.noti.dto.ChatNotiDto;
import com.sparos.uniquone.msapostservice.noti.dto.NotiOutDto;
import com.sparos.uniquone.msapostservice.noti.repository.EmitterRepositoryImpl;
import com.sparos.uniquone.msapostservice.noti.repository.INotiRepository;
import com.sparos.uniquone.msapostservice.offer.domain.Offer;
import com.sparos.uniquone.msapostservice.post.repository.IPostImgRepository;
import com.sparos.uniquone.msapostservice.qna.domain.QnA;
import com.sparos.uniquone.msapostservice.util.feign.dto.Chat;
import com.sparos.uniquone.msapostservice.util.feign.service.IUserConnect;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmitterServiceImpl implements IEmitterService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60 * 24;

    private final EmitterRepositoryImpl emitterRepository;

    private final INotiRepository iNotiRepository;
    private final ICornRepository iCornRepository;
    private final IPostImgRepository iPostImgRepository;
    private final IUserConnect iUserConnect;

    private String makeTimeIncludeId(Long userId) {
        return userId + "_" + System.currentTimeMillis();
    }

    @Override
    public SseEmitter subscribe(Long userId, String lastEventId) {
        // 1
        String id = makeTimeIncludeId(userId);

        // 2
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        // 3
        // 503 에러를 방지하기 위한 더미 이벤트 전송
        sendToClient(emitter, id, "EventStream Created. [userId=" + userId + "]");

        // 4
        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;
    }

    @Override
    public void send(Long userId, Object object, NotiType notiType) {
        Noti notification = createNotification(userId, object, notiType, false);
        String id = String.valueOf(userId);

        // 로그인 한 유저의 SseEmitter 모두 가져오기
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByUserId(id);
        sseEmitters.forEach(
                (key, emitter) -> {
                    // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
                    emitterRepository.saveEventCache(key, notification);
                    // 데이터 전송
                    sendToClient(emitter, key, entityToNotiOutDto(notification));
                }
        );
    }

    @Override
    public ChatNotiDto sendChatPush(Long userId, Long postId, Chat chat) {

        ChatNotiDto notification = ChatNotiDto.builder()
                .notiType(NotiType.CHAT)
                .nickName(iUserConnect.getUserNickName(chat.getSenderId()).getNickname())
                .dsc("님이 채팅을 보냈습니다.")
                .chatRoomId(chat.getChatRoomId())
                .userCornImg(iCornRepository.findImgUrlByUserId(chat.getSenderId()))
                .postImg(iPostImgRepository.findUrlByPostId(postId))
                .build();

        String id = String.valueOf(userId);

        // 로그인 한 유저의 SseEmitter 모두 가져오기
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByUserId(id);
        sseEmitters.forEach(
                (key, emitter) -> {
                    // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
                    emitterRepository.saveEventCache(key, notification);
                    // 데이터 전송
                    sendToClient(emitter, key, notification);
                }
        );

        return notification;
    }

    // 3
    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            log.error(String.valueOf(exception));
            log.error(exception.getMessage());
//            emitterRepository.deleteById(id);
//            throw new RuntimeException("연결 오류!");
        }
    }

    private Noti createNotification(Long userId, Object object, NotiType notiType, Boolean isCheck) {

        Noti noti = Noti.builder()
                .userId(userId)
                .notiType(notiType)
                .isCheck(isCheck)
                .build();

        switch (notiType){
            case COOL:
                noti.setCool((Cool) object);
                noti.setNickname(iUserConnect.getUserNickName(((Cool) object).getUserId()).getNickname());
                noti.setUserCornImg(iCornRepository.findImgUrlByUserId(userId));
                noti.setPostImg(iPostImgRepository.findUrlByPostId(((Cool) object).getPost().getId()));
                noti.setDsc("님이 회원님의 포스트를 좋아합니다.");
                break;
            case COMMENT:
                noti.setComment((Comment) object);
                noti.setNickname(((Comment) object).getUserNickName());
                noti.setUserCornImg(iCornRepository.findImgUrlByUserId(((Comment) object).getUserId()));
                noti.setPostImg(iPostImgRepository.findUrlByPostId(((Comment) object).getPost().getId()));
                noti.setDsc("님이 댓글을 작성하였습니다. : " + ((Comment) object).getContent());
                break;
            case FOLLOW:
                noti.setFollow((Follow) object);
                noti.setNickname(iUserConnect.getUserNickName(((Follow) object).getUserId()).getNickname());
                noti.setUserCornImg(iCornRepository.findImgUrlByUserId(((Follow) object).getUserId()));
//                noti.setPostImg(iPostImgRepository.findUrlByPostId(((Comment) object).getPost().getId()));
                noti.setDsc("님이 회원님을 팔로우하기 시작했습니다.");
                break;
            case OFFER:
                noti.setOffer((Offer) object);
                noti.setNickname(iUserConnect.getUserNickName(((Offer) object).getUserId()).getNickname());
                noti.setUserCornImg(iCornRepository.findImgUrlByUserId(((Offer) object).getUserId()));
                noti.setPostImg(iPostImgRepository.findUrlByPostId(((Offer) object).getPost().getId()));
                noti.setDsc("님이 오퍼를 보냈습니다.");
                break;
            case OFFER_ACCEPT:
                noti.setOffer((Offer) object);
                noti.setNickname(iUserConnect.getUserNickName(((Offer) object).getPost().getCorn().getUserId()).getNickname());
                noti.setUserCornImg(iCornRepository.findImgUrlByUserId(((Offer) object).getPost().getCorn().getUserId()));
                noti.setPostImg(iPostImgRepository.findUrlByPostId(((Offer) object).getPost().getId()));
                noti.setDsc("님이 오퍼를 수락했습니다.");
                break;
            case OFFER_REFUSE:
                noti.setOffer((Offer) object);
                noti.setNickname(iUserConnect.getUserNickName(((Offer) object).getPost().getCorn().getUserId()).getNickname());
                noti.setUserCornImg(iCornRepository.findImgUrlByUserId(((Offer) object).getPost().getCorn().getUserId()));
                noti.setPostImg(iPostImgRepository.findUrlByPostId(((Offer) object).getPost().getId()));
                noti.setDsc("님이 오퍼를 거절했습니다.");
                break;
            case QNA:
                noti.setQna((QnA) object);
                noti.setNickname("ADMIN");
                noti.setDsc("문의글에 답글이 작성되었습니다. : " + ((QnA) object).getAnswer());
                break;
        }

        return iNotiRepository.save(noti);
    }

    private NotiOutDto entityToNotiOutDto(Noti notification) {

        Long typeId = 0l;

        switch (notification.getNotiType()){
            case COOL:
                typeId = notification.getCool().getPost().getId();
                break;
            case COMMENT:
                typeId = notification.getComment().getPost().getId();
                break;
            case FOLLOW:
                Corn corn = iCornRepository.findByUserId(notification.getUserId()).get();
                typeId = corn.getId();
                break;
            case OFFER:
            case OFFER_ACCEPT:
            case OFFER_REFUSE:
                typeId = notification.getOffer().getId();
                break;
            case QNA:
                typeId = notification.getQna().getId();
                break;
        }

        String date = notification.getRegDate().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일"));
        String time = notification.getRegDate().format(DateTimeFormatter.ofPattern("a hh:mm"));

        return NotiOutDto.builder()
                .notiType(notification.getNotiType())
                .notiId(notification.getId())
                .typeId(typeId)
                .nickName(notification.getNickName())
                .userCornImg(notification.getUserCornImg())
                .dsc(notification.getDsc())
                .isCheck(notification.getIsCheck())
                .date(date)
                .regTime(time)
                .postImg(notification.getPostImg())
                .build();
    }
}