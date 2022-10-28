package com.sparos.uniquone.msapostservice.offer.controller;

import com.sparos.uniquone.msapostservice.offer.dto.OfferCheckedInPutDto;
import com.sparos.uniquone.msapostservice.offer.dto.OfferInputDto;
import com.sparos.uniquone.msapostservice.offer.service.IOfferService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/offer")
@RequiredArgsConstructor
@RestController
public class OfferController {

    private final IOfferService iOfferService;

    // 오퍼 보내기
   @PostMapping("")
    public ResponseEntity<SuccessResponse> sendOffer(@RequestBody OfferInputDto offerInputDto, HttpServletRequest request) {
        JSONObject jsonObject = iOfferService.sendOffer(offerInputDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 콘이 받은 오퍼 조회
    @GetMapping("/corn")
    public ResponseEntity<SuccessResponse> findCornOffer(HttpServletRequest request) {
        JSONObject jsonObject = iOfferService.findCornOffer(request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 콘이 받은 오퍼 상세 조회
    @GetMapping("/corn/{postId}")
    public ResponseEntity<SuccessResponse> findCornOfferDetail(@PathVariable Long postId, HttpServletRequest request) {
        JSONObject jsonObject = iOfferService.findCornOfferDetail(postId, request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 회원이 보낸 오퍼 조회
    @GetMapping("")
    public ResponseEntity<SuccessResponse> findMyOffer(HttpServletRequest request) {
        JSONObject jsonObject = iOfferService.findMyOffer(request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 오퍼 확인
    @PatchMapping("")
    public ResponseEntity<SuccessResponse> offerChecked(@RequestBody OfferCheckedInPutDto offerCheckedInPutDto, HttpServletRequest request) {
        JSONObject jsonObject = iOfferService.offerChecked(offerCheckedInPutDto ,request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

}
