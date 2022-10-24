package com.sparos.uniquone.msapostservice.noti.repository;


import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface IEmitterRepository {
    SseEmitter save(String emitterId, SseEmitter sseEmitter);
    void saveEventCache(String emitterId, Object event);
    Map<String, SseEmitter> findAllEmitterStartWithByUserId(String userId);
    Map<String, Object> findAllEventCacheStartWithByUserId(String userId);
    void deleteById(String id);
    void deleteAllEmitterStartWithUserId(String userId);
    void deleteAllEventCacheStartWithUserId(String userId);

}
