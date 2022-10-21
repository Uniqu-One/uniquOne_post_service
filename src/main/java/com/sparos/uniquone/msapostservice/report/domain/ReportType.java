package com.sparos.uniquone.msapostservice.report.domain;

public enum ReportType {
    BAD_CONTENT("나쁜 게시물");

    String reportType;

    ReportType(String reportType){
        this.reportType =reportType;
    }

    public String value(){
        return reportType;
    }
}
