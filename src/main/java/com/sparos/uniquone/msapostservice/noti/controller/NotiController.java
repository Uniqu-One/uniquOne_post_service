package com.sparos.uniquone.msapostservice.noti.controller;

import com.sparos.uniquone.msapostservice.noti.service.IEmitterService;
import com.sparos.uniquone.msapostservice.noti.service.INotiService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/noti")
@RequiredArgsConstructor
@RestController
public class NotiController {

    private final INotiService iNotiService;
    private final IEmitterService iEmitterService;

    // 알림 구독
    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long id,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return iEmitterService.subscribe(id, lastEventId);
    }

    // 알림 내역 조회
    @GetMapping(value = "")
    public ResponseEntity<SuccessResponse> findMyNoti(HttpServletRequest request) {
        JSONObject jsonObject = iNotiService.findMyNoti(request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }


    // 알림 확인
}

