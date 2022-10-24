package com.sparos.uniquone.msapostservice.report.service;

import com.sparos.uniquone.msapostservice.report.dto.ReportInputDto;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface IReportService {

    // 신고 등록
    JSONObject createReport(ReportInputDto reportInputDto, HttpServletRequest request);

    // 모든 신고 내역 조회
    JSONObject findAllReport();

}
