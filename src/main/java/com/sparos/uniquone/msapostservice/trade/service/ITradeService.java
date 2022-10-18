package com.sparos.uniquone.msapostservice.trade.service;

import com.sparos.uniquone.msapostservice.trade.dto.TradeInputDto;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface ITradeService {

    // 거래 등록
    JSONObject createTrade(TradeInputDto tradeInputDto, HttpServletRequest request);

    // 판매 내역 조회
    JSONObject findSell(HttpServletRequest request);

    // 구매 내역 조회
    JSONObject findBuy(HttpServletRequest request);
}
