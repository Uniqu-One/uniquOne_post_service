package com.sparos.uniquone.msapostservice.report.domain;

import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.report.dto.ReportInputDto;
import com.sparos.uniquone.msapostservice.report.dto.ReportOutDto;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;

public class ReportUtils {

    public static Report reportInputDtoToEntity(ReportInputDto reportInputDto, Post post, HttpServletRequest request) {
        return Report.builder()
                .userId(JwtProvider.getUserPkId(request))
                .post(post)
                .reportType(reportInputDto.getReportType())
                .build();
    }


    public static ReportOutDto entityToReportOutDto(Report report) {
        return ReportOutDto.builder()
                .reportId(report.getId())
                .userId(report.getUserId())
                .postId(report.getPost().getId())
                .reportType(report.getReportType())
                .regDate(report.getRegDate().format(DateTimeFormatter.ofPattern("yyyy년M월dd일 hh:mm")))
                .build();
    }

}
