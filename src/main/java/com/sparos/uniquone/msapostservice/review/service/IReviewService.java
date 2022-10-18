package com.sparos.uniquone.msapostservice.review.service;

import com.sparos.uniquone.msapostservice.review.dto.ReviewCreateDto;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface IReviewService {

    // 후기 작성
    JSONObject createReview(ReviewCreateDto reviewCreateDto, HttpServletRequest request);

    // 나의 콘 후기 조회
    JSONObject findMyCornReview(HttpServletRequest request);

    // 다른 유저 콘 후기 조회
    JSONObject findOtherCornReview(Long cornId, HttpServletRequest request);

    // 작성 한 후기 조회
    JSONObject findMyReview(HttpServletRequest request);
}
