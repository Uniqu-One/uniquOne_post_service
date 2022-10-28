package com.sparos.uniquone.msapostservice.eco.service;

import org.json.simple.JSONObject;

public interface IEcoService {

    void run();

    // 어제 탄소 절감 조회
    JSONObject findYesterdayEco();

    // 총 탄소 절감 조회
    JSONObject findAllEco();

    // 거래에서 어제 날짜 카테고리 별 카운트 가져오기 테스트
    JSONObject test();

    // 카운트 카테고리 별 에코 계산 총 합
    JSONObject test1();
}
