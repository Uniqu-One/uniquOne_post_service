package com.sparos.uniquone.msapostservice.report.dto;

import com.sparos.uniquone.msapostservice.report.domain.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportOutDto {

    private Long reportId;
    private Long userId;
    private Long postId;
    private ReportType reportType;
    private String regDate;

}
