package com.sparos.uniquone.msapostservice.qna.controller;

import com.sparos.uniquone.msapostservice.qna.dto.QuestionInputDto;
import com.sparos.uniquone.msapostservice.qna.service.IQnAService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/qna")
@RequiredArgsConstructor
@RestController
public class QnAController {

    private final IQnAService iQnAService;

    // 문의 등록
    @PostMapping("")
    public ResponseEntity<SuccessResponse> createQuestion(@RequestBody QuestionInputDto questionInputDto, HttpServletRequest request) {
        JSONObject jsonObject = iQnAService.createQuestion(questionInputDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 나의 문의 내역 조회
    @GetMapping("")
    public ResponseEntity<SuccessResponse> findMyQnA(HttpServletRequest request) {
        JSONObject jsonObject = iQnAService.findMyQnA(request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 나의 문의 개별 조회
    @GetMapping("/{qnaId}")
    public ResponseEntity<SuccessResponse> findMyDetailQnA(@PathVariable Long qnaId, HttpServletRequest request) {
        JSONObject jsonObject = iQnAService.findMyDetailQnA(qnaId, request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

}
