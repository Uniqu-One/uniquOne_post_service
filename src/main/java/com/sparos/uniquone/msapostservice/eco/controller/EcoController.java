package com.sparos.uniquone.msapostservice.eco.controller;

import com.sparos.uniquone.msapostservice.eco.service.IEcoService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/eco")
@RequiredArgsConstructor
@RestController
public class EcoController {

    private final IEcoService iEcoService;

    // 콘이 받은 오퍼 조회
    @GetMapping("")
    public ResponseEntity<SuccessResponse> findCornOffer() {
        JSONObject jsonObject = iEcoService.test();
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }



}

