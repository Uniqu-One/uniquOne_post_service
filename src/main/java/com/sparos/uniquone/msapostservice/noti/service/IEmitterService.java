package com.sparos.uniquone.msapostservice.noti.service;

import com.sparos.uniquone.msapostservice.noti.domain.NotiType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface IEmitterService {

    // 알림 구독
    SseEmitter subscribe(Long userId, String lastEventId);

    // 알림 전송
    void send(Long userId, Object object, NotiType notiType);

}
