package com.sparos.uniquone.msapostservice.review.controller;

import com.sparos.uniquone.msapostservice.review.dto.ReviewCreateDto;
import com.sparos.uniquone.msapostservice.review.service.IReviewService;
import com.sparos.uniquone.msapostservice.util.response.ExceptionResponse;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/reviews")
@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final IReviewService iReviewService;

    // 후기 작성
    @PostMapping("")
    public ResponseEntity<SuccessResponse> createReview(@RequestBody ReviewCreateDto reviewCreateDto, HttpServletRequest request) {
        JSONObject jsonObject = iReviewService.createReview(reviewCreateDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 나의 콘 후기 조회
    @GetMapping("")
    public ResponseEntity<SuccessResponse> findMyCornReview(HttpServletRequest request) {
        JSONObject jsonObject = iReviewService.findMyCornReview(request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 다른 유저 콘 후기 조회
    @GetMapping("/corn/{cornId}")
    public ResponseEntity<SuccessResponse> findOtherCornReview(@PathVariable Long cornId, HttpServletRequest request) {
        JSONObject jsonObject = iReviewService.findOtherCornReview(cornId, request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 작성 한 후기 조회
    @GetMapping("/my")
    public ResponseEntity<SuccessResponse> findMyReview(HttpServletRequest request) {
        JSONObject jsonObject = iReviewService.findMyReview(request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }
}
