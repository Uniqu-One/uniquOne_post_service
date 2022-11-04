package com.sparos.uniquone.msapostservice.util.response;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    FAIL_CODE("F001","OK"),

    NO_SUCH_ELEMENT_EXCEPTION("F002","데이터를 찾을 수 없습니다."),
    POST_TYPE_NOT_TRADE("F003","게시물이 거래가 가능 한 상태가 아닙니다."),

    INVALID_USERID("F004","유저 정보가 일치 하지 않습니다."),

    KEYWORD_EMPTY("F005","키워드를 입력 해 주세요"),
    INVALID_TOKEN("F006" ,"올바르지 않은 토큰 입니다."),

    Expired_TOKEN("F007", "토큰 유효시간이 지났습니다."),

    UNSUPPORTED_TOKEN("F008", "지원하지 않는 토큰 입니다."),

    EMPTY_PAYLOAD_TOKEN("F009", "토큰 내용이 비어 있습니다."),

    EXISTS_UNISTAR("F010","해당 유니스타가 이미 존재합니다."),

    NOTFOUND_POST_USER_UNISTAR("F011","해당 포스트에 유저의 유니스타가 존재 하지 않습니다."),

    NOTFOUND_ADMIN_BOARD("F012","게시판이 존재 하지 않습니다."),

    NOTFOUND_UNISTAR("F013","유니스타가 존재 하지 않습니다."),

    NOTFOUND_CORN("F014","콘이 존재 하지 않습니다."),
    NOTFOUND_POST("F015","포스트가 존재 하지 않습니다.")
    ;


    private String code;
    private String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message =message;
    }
}
