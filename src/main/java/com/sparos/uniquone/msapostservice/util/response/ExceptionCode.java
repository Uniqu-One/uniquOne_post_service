package com.sparos.uniquone.msapostservice.util.response;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    FAIL_CODE("F001","OK"),

    NO_SUCH_ELEMENT_EXCEPTION("F002","데이터를 찾을 수 없습니다."),
    POST_TYPE_NOT_TRADE("F003","게시물이 거래가 가능 한 상태가 아닙니다."),
    NO_SUCH("F002","NoSuchException");


    private String code;
    private String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message =message;
    }
}
