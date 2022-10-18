package com.sparos.uniquone.msapostservice.trade.controller;

import com.sparos.uniquone.msapostservice.review.dto.ReviewCreateDto;
import com.sparos.uniquone.msapostservice.trade.dto.TradeInputDto;
import com.sparos.uniquone.msapostservice.trade.service.ITradeService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/trade")
@RequiredArgsConstructor
@RestController
public class TradeController {

    // todo userId 토큰 대체

    private final ITradeService iTradeService;

    // 거래 등록
    @PostMapping("")
    public ResponseEntity<SuccessResponse> createTrade(@RequestBody TradeInputDto tradeInputDto, HttpServletRequest request) {
        JSONObject jsonObject = iTradeService.createTrade(tradeInputDto, request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 판매 내역 조회
    @GetMapping("/sell")
    public ResponseEntity<SuccessResponse> findSell(HttpServletRequest request) {
        JSONObject jsonObject = iTradeService.findSell(request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 구매 내역 조회
    @GetMapping("/buy")
    public ResponseEntity<SuccessResponse> findBuy(HttpServletRequest request) {
        JSONObject jsonObject = iTradeService.findBuy(request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

}
