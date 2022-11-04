package com.sparos.uniquone.msapostservice.qna.controller;

import com.sparos.uniquone.msapostservice.qna.dto.AnswerInputDto;
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

@RequestMapping("/admin/qna")
@RequiredArgsConstructor
@RestController
public class AdminQnAController {

    private final IQnAService iQnAService;

    // 모든 문의 리스트 조회
    @GetMapping("")
    public ResponseEntity<SuccessResponse> findAllQnA() {
        JSONObject jsonObject = iQnAService.findAllQnA();
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 문의 상세 조회
    @GetMapping("/{qnaId}")
    public ResponseEntity<SuccessResponse> findDetailQnA(@PathVariable Long qnaId) {
        JSONObject jsonObject = iQnAService.findDetailQnA(qnaId);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

    // 문의 답변 등록
    @PatchMapping("")
    public ResponseEntity<SuccessResponse> createAnswer(@RequestBody AnswerInputDto answerInputDto) {
        JSONObject jsonObject = iQnAService.createAnswer(answerInputDto);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

}
