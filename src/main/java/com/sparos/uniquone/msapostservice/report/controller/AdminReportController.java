package com.sparos.uniquone.msapostservice.report.controller;

import com.sparos.uniquone.msapostservice.qna.dto.AnswerInputDto;
import com.sparos.uniquone.msapostservice.qna.service.IQnAService;
import com.sparos.uniquone.msapostservice.report.service.IReportService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/report")
@RequiredArgsConstructor
@RestController
public class AdminReportController {

    private final IReportService iReportService;

    // 신고 내역 조회
    @GetMapping("")
    public ResponseEntity<SuccessResponse> findAllReport() {
        JSONObject jsonObject = iReportService.findAllReport();
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

}
