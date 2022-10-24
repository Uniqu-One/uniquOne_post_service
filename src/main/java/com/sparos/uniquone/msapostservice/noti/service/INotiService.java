package com.sparos.uniquone.msapostservice.noti.service;

import com.sparos.uniquone.msapostservice.noti.domain.NotiType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface INotiService {

    SseEmitter subscribe(Long userId, String lastEventId);

    void send(Long userId, Object object, NotiType notiType);

}

