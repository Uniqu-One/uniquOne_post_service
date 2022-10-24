package com.sparos.uniquone.msapostservice.report.domain;

public enum ReportType {

    DISGUSTING("선정적이거나 혐오감을 주는 게시물"),
    ADVERTISEMENT("광고성 게시물"),
    OVERLAP("중복 된 게시물"),
    ETC("기타 사유");

    String reportType;

    ReportType(String reportType){
        this.reportType =reportType;
    }

    public String value(){
        return reportType;
    }
}
