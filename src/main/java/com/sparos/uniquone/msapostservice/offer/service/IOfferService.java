package com.sparos.uniquone.msapostservice.offer.service;

import com.sparos.uniquone.msapostservice.offer.domain.OfferType;
import com.sparos.uniquone.msapostservice.offer.dto.OfferCheckedInPutDto;
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

    // 회원이 보낸 오퍼 조회
    JSONObject findMyOffer(HttpServletRequest request);

    // 오퍼 확인
    JSONObject offerChecked(OfferCheckedInPutDto offerCheckedInPutDto, HttpServletRequest request);
}
