package com.sparos.uniquone.msapostservice.report.domain;

public enum ReportType {
    BAD_POST("선정적이거나 혐오감을 주는 게시물"),
    BAD_USER("광고성 게시물"),
    REPEAT_POST("중복 된 게시물"),
    OTHER("기타 사유");

    String reportType;

    ReportType(String reportType){
        this.reportType =reportType;
    }

    public String value(){
        return reportType;
    }
}
