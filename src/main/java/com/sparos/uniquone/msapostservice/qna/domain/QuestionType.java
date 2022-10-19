package com.sparos.uniquone.msapostservice.qna.domain;

public enum QuestionType {
    POST("포스트"),
    COMMENT("댓글"),
    TRADE("거래"),
    SERVICE("서비스");

    String questionType;

    QuestionType(String questionType){
        this.questionType =questionType;
    }

    public String value(){
        return questionType;
    }
}
