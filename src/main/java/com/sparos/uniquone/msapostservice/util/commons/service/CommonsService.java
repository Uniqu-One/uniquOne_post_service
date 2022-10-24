package com.sparos.uniquone.msapostservice.util.commons.service;

import com.sparos.uniquone.msapostservice.util.commons.dto.response.CommonsUserResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface CommonsService {
    CommonsUserResponseDto getCommonsUserInfoByToken(HttpServletRequest request);
}
