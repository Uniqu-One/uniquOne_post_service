package com.sparos.uniquone.msapostservice.noti.service;

import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import com.sparos.uniquone.msapostservice.cool.domain.Cool;
import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.follow.domain.Follow;
import com.sparos.uniquone.msapostservice.noti.domain.Noti;
import com.sparos.uniquone.msapostservice.noti.domain.NotiType;
import com.sparos.uniquone.msapostservice.noti.domain.NotiUtils;
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

        // 503 ????????? ???????????? ?????? ?????? ????????? ??????
        sendToClient(emitter, id, "EventStream Created. [userId=" + userId + "]");

        // 4
        // ?????????????????? ???????????? Event ????????? ????????? ?????? ???????????? Event ????????? ??????
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

        // ????????? ??? ????????? SseEmitter ?????? ????????????
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByUserId(id);
        sseEmitters.forEach(
                (key, emitter) -> {
                    // ????????? ?????? ??????(????????? ????????? ???????????? ??????)
                    emitterRepository.saveEventCache(key, notification);
                    // ????????? ??????
                    sendToClient(emitter, key, entityToNotiOutDto(notification));
                }
        );
    }

    @Override
    public ChatNotiDto sendChatPush(Long userId, Long postId, Chat chat) {

        ChatNotiDto notification = ChatNotiDto.builder()
                .notiType(NotiType.CHAT)
                .nickName(iUserConnect.getUserNickName(chat.getSenderId()).getNickname())
                .dsc("?????? ????????? ???????????????.")
                .chatRoomId(chat.getChatRoomId())
                .userCornImg(iCornRepository.findImgUrlByUserId(chat.getSenderId()))
                .postImg(iPostImgRepository.findUrlByPostId(postId))
                .build();

        String id = String.valueOf(userId);

        // ????????? ??? ????????? SseEmitter ?????? ????????????
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByUserId(id);
        sseEmitters.forEach(
                (key, emitter) -> {
                    // ????????? ?????? ??????(????????? ????????? ???????????? ??????)
                    emitterRepository.saveEventCache(key, notification);
                    // ????????? ??????
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
//            throw new RuntimeException("?????? ??????!");
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
                noti.setUserCornImg(iCornRepository.findImgUrlByUserId(((Cool) object).getUserId()));
                noti.setPostImg(iPostImgRepository.findUrlByPostId(((Cool) object).getPost().getId()));
                noti.setDsc("?????? ???????????? ???????????? ???????????????.");
                break;
            case COMMENT:
                noti.setComment((Comment) object);
                noti.setNickname(((Comment) object).getUserNickName());
                noti.setUserCornImg(iCornRepository.findImgUrlByUserId(((Comment) object).getUserId()));
                noti.setPostImg(iPostImgRepository.findUrlByPostId(((Comment) object).getPost().getId()));
                noti.setDsc("?????? ????????? ?????????????????????. : " + ((Comment) object).getContent());
                break;
            case FOLLOW:
                noti.setFollow((Follow) object);
                noti.setNickname(iUserConnect.getUserNickName(((Follow) object).getUserId()).getNickname());
                noti.setUserCornImg(iCornRepository.findImgUrlByUserId(((Follow) object).getUserId()));
//                noti.setPostImg(iPostImgRepository.findUrlByPostId(((Comment) object).getPost().getId()));
                noti.setDsc("?????? ???????????? ??????????????? ??????????????????.");
                break;
            case OFFER:
                noti.setOffer((Offer) object);
                noti.setNickname(iUserConnect.getUserNickName(((Offer) object).getUserId()).getNickname());
                noti.setUserCornImg(iCornRepository.findImgUrlByUserId(((Offer) object).getUserId()));
                noti.setPostImg(iPostImgRepository.findUrlByPostId(((Offer) object).getPost().getId()));
                noti.setDsc("?????? ????????? ???????????????.");
                break;
            case OFFER_ACCEPT:
                noti.setOffer((Offer) object);
                noti.setNickname(iUserConnect.getUserNickName(((Offer) object).getPost().getCorn().getUserId()).getNickname());
                noti.setUserCornImg(iCornRepository.findImgUrlByUserId(((Offer) object).getPost().getCorn().getUserId()));
                noti.setPostImg(iPostImgRepository.findUrlByPostId(((Offer) object).getPost().getId()));
                noti.setDsc("?????? ????????? ??????????????????.");
                break;
            case OFFER_REFUSE:
                noti.setOffer((Offer) object);
                noti.setNickname(iUserConnect.getUserNickName(((Offer) object).getPost().getCorn().getUserId()).getNickname());
                noti.setUserCornImg(iCornRepository.findImgUrlByUserId(((Offer) object).getPost().getCorn().getUserId()));
                noti.setPostImg(iPostImgRepository.findUrlByPostId(((Offer) object).getPost().getId()));
                noti.setDsc("?????? ????????? ??????????????????.");
                break;
            case QNA:
                noti.setQna((QnA) object);
                noti.setNickname("ADMIN");
                noti.setDsc("???????????? ????????? ?????????????????????. : " + ((QnA) object).getAnswer());
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

        return NotiOutDto.builder()
                .notiType(notification.getNotiType())
                .notiId(notification.getId())
                .typeId(typeId)
                .nickName(notification.getNickName())
                .userCornImg(notification.getUserCornImg())
                .dsc(notification.getDsc())
                .isCheck(notification.getIsCheck())
                .regDate(NotiUtils.converter(notification.getRegDate()))
                .postImg(notification.getPostImg())
                .build();
    }
}