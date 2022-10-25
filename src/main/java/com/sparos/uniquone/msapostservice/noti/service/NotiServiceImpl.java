package com.sparos.uniquone.msapostservice.noti.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class NotiServiceImpl implements INotiService {

    @Override
    public JSONObject findMyNoti(HttpServletRequest request) {
        return null;
    }
}