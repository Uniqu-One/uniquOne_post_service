package com.sparos.uniquone.msapostservice.util.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    FAIL_CODE("F001","OK"),

    NO_SUCH_ELEMENT_EXCEPTION("F002","데이터를 찾을 수 없습니다."),
    POST_TYPE_NOT_TRADE("F003","게시물이 거래가 가능 한 상태가 아닙니다."),

    INVALID_USERID("F004","유저 정보가 일치 하지 않습니다."),

    KEYWORD_EMPTY("F005","키워드를 입력 해 주세요"),

    INVALID_TOKEN("F005" ,"올바르지 않은 토큰 입니다."),

    ;


    private String code;
    private String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message =message;
    }
}
