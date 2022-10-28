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

    // 어제의 탄소 절감 조회
    @GetMapping("")
    public ResponseEntity<SuccessResponse> findYesterdayEco() {
        JSONObject jsonObject = iEcoService.findYesterdayEco();
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 총 탄소 절감 조회
    @GetMapping("/all")
    public ResponseEntity<SuccessResponse> findAllEco() {
        JSONObject jsonObject = iEcoService.findAllEco();
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }


    // 거래에서 어제 날짜 카테고리 별 카운트 가져오기 테스트
    @GetMapping("/test")
    public ResponseEntity<SuccessResponse> findCornOffer() {
        JSONObject jsonObject = iEcoService.test();
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 카운트 카테고리 별 에코 계산 총 합 테스트
    @GetMapping("/test1")
    public ResponseEntity<SuccessResponse> test1() {
        JSONObject jsonObject = iEcoService.test1();
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }


}

