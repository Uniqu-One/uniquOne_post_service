package com.sparos.uniquone.msapostservice.offer.service;

import com.sparos.uniquone.msapostservice.offer.dto.OfferInputDto;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface IOfferService {

    // 오퍼 보내기
    JSONObject sendOffer(OfferInputDto offerInputDto, HttpServletRequest request);

    // 콘이 받은 오퍼 조회
    JSONObject findCornOffer(HttpServletRequest request);

    // 콘이 받은 오퍼 상세 조회
    JSONObject findCornOfferDetail(Long postId, HttpServletRequest request);
}
