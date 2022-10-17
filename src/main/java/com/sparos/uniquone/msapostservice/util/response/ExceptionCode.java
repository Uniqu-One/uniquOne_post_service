package com.sparos.uniquone.msapostservice.util.response;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    FAIL_CODE("F001","OK"),
    NO_SUCH("F002","NoSuchException");


    private String code;
    private String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message =message;
    }
}
