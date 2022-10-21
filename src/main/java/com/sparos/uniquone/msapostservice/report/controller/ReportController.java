package com.sparos.uniquone.msapostservice.report.controller;

import com.sparos.uniquone.msapostservice.report.dto.ReportInputDto;
import com.sparos.uniquone.msapostservice.report.service.IReportService;
import com.sparos.uniquone.msapostservice.util.response.SuccessCode;
import com.sparos.uniquone.msapostservice.util.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/report")
@RequiredArgsConstructor
@RestController
public class ReportController {

    private final IReportService iReportService;

    // 신고 등록
    @PostMapping("")
    public ResponseEntity<SuccessResponse> createReport(@RequestBody ReportInputDto reportInputDto, HttpServletRequest request) {
        JSONObject jsonObject = iReportService.createReport(reportInputDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.of(SuccessCode.SUCCESS_CODE, jsonObject.get("data")));
    }

}
