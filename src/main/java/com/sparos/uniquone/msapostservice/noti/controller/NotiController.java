/*
package com.sparos.uniquone.msapostservice.noti.controller;

import com.sparos.uniquone.msapostservice.noti.repository.INotiRepository;
import com.sparos.uniquone.msapostservice.noti.service.INotiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequestMapping("/noti")
@RequiredArgsConstructor
@RestController
public class NotiController {

    private final INotiService iNotiService;

    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long id,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return iNotiService.subscribe(id, lastEventId);
    }
}
*/
