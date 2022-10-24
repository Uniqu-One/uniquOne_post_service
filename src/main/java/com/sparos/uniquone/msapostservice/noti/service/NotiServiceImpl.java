package com.sparos.uniquone.msapostservice.noti.service;

import com.sparos.uniquone.msapostservice.comment.domain.Comment;
import com.sparos.uniquone.msapostservice.cool.domain.Cool;
import com.sparos.uniquone.msapostservice.follow.domain.Follow;
import com.sparos.uniquone.msapostservice.noti.domain.Noti;
import com.sparos.uniquone.msapostservice.noti.domain.NotiType;
import com.sparos.uniquone.msapostservice.noti.dto.NotiResponseDto;
import com.sparos.uniquone.msapostservice.noti.repository.EmitterRepositoryImpl;
import com.sparos.uniquone.msapostservice.noti.repository.INotiRepository;
import com.sparos.uniquone.msapostservice.qna.domain.QnA;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotiServiceImpl implements INotiService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepositoryImpl emitterRepository;

    private final INotiRepository iNotiRepository;

    private final ApplicationEventPublisher eventPublisher;

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

    // 3
    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("연결 오류!");
        }
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
                    sendToClient(emitter, key, entityToDto(notification));
                }
        );
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
                noti.setDsc(((Cool) object).getUserId().toString());
            case COMMENT:
                noti.setComment((Comment) object);
                noti.setDsc(((Comment) object).getContent());
            case FOLLOW:
                noti.setFollow((Follow) object);
                noti.setDsc(((Follow) object).getUserId().toString());
            case QNA:
                noti.setQna((QnA) object);
                noti.setDsc(((QnA) object).getAnswer());
        }

        return iNotiRepository.save(noti);
    }

    private NotiResponseDto entityToDto(Noti notification) {

        Long typeId = 0l;
        switch (notification.getNotiType()){
            case COOL:
                typeId = notification.getCool().getId();
            case COMMENT:
                typeId = notification.getComment().getId();
            case FOLLOW:
                typeId = notification.getFollow().getId();
            case QNA:
                typeId = notification.getQna().getId();
        }

        return NotiResponseDto.builder()
                .notiType(notification.getNotiType())
                .notiId(notification.getId())
                .typeId(typeId)
                .dsc(notification.getDsc())
                .isCheck(notification.getIsCheck())
                .regDate(notification.getRegDate())
                .build();
    }
}