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
import java.util.Map;

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
    @GetMapping(value = "/{pageNum}")
    public ResponseEntity<SuccessResponse> findMyNoti(@PathVariable int pageNum, HttpServletRequest request) {
        JSONObject jsonObject = iNotiService.findMyNoti(pageNum, request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 알림 확인
    @PatchMapping(value = "")
    public ResponseEntity<SuccessResponse> notiChecked(@RequestBody Map<String, Long> notiId, HttpServletRequest request) {
        JSONObject jsonObject = iNotiService.notiChecked(notiId.get("notiId"), request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }
}

