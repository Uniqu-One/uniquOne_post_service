package com.sparos.uniquone.msapostservice.noti.service;

import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface INotiService {

    // 알림 내역 조회
    JSONObject findMyNoti(int pageNum, HttpServletRequest request);

    // 알림 확인
    JSONObject notiChecked(Long notiId, HttpServletRequest request);

    // 확인 안한 알림 갯수
    JSONObject notiNonCheckedCnt(HttpServletRequest request);

}

