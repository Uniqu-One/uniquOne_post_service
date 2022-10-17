package com.sparos.uniquone.msapostservice.review.controller;

import com.sparos.uniquone.msapostservice.review.domain.Review;
import com.sparos.uniquone.msapostservice.review.dto.ReviewCreateDto;
import com.sparos.uniquone.msapostservice.review.service.IReviewService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reviews")
@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final IReviewService iReviewService;

    // todo userId 토큰 대체
    // 후기 작성
    @PostMapping("")
    public ResponseEntity<SuccessResponse> createReview(@RequestBody ReviewCreateDto reviewCreateDto) {
        JSONObject jsonObject = iReviewService.createReview(reviewCreateDto);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, (String) jsonObject.get("result"), jsonObject.get("data")));
    }

    // 나의 콘 후기 조회
    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse> findMyCornReview(@PathVariable Long userId) {
        JSONObject jsonObject = iReviewService.findMyCornReview(userId);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, (String) jsonObject.get("result"), jsonObject.get("data")));
    }

    // 다른 유저 콘 후기 조회
    @GetMapping("/corn/{cornId}")
    public ResponseEntity<SuccessResponse> findOtherCornReview(@PathVariable Long cornId) {
        JSONObject jsonObject = iReviewService.findOtherCornReview(cornId);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, (String) jsonObject.get("result"), jsonObject.get("data")));
    }


    // todo
    // 작성 한 후기 조회
    @GetMapping("/my/{userId}")
    public ResponseEntity<SuccessResponse> findMyReview(@PathVariable Long userId) {
        JSONObject jsonObject = iReviewService.findMyReview(userId);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, (String) jsonObject.get("result"), jsonObject.get("data")));
    }
}
