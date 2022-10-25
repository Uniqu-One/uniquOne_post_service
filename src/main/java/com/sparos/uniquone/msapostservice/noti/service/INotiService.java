package com.sparos.uniquone.msapostservice.noti.service;

import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface INotiService {

    JSONObject findMyNoti(HttpServletRequest request);

}

