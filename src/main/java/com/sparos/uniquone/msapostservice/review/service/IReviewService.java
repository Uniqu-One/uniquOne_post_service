package com.sparos.uniquone.msapostservice.review.service;

import com.sparos.uniquone.msapostservice.review.dto.ReviewCreateDto;
import org.json.simple.JSONObject;

public interface IReviewService {

    // 후기 작성
    JSONObject createReview(ReviewCreateDto reviewCreateDto);

    // 나의 콘 후기 조회
    JSONObject findMyCornReview(Long userId);

    // 다른 유저 콘 후기 조회
    JSONObject findOtherCornReview(Long cornId);

    // 작성 한 후기 조회
    JSONObject findMyReview(Long userId);
}
