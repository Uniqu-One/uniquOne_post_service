package com.sparos.uniquone.msapostservice.util.response;

import lombok.Getter;

@Getter
public class UniquOneServiceException extends RuntimeException {

    private ExceptionCode exceptionCode;
    private String data;

    public UniquOneServiceException(ExceptionCode code) {
        this.exceptionCode = code;
        this.data = null;
    }

    /*public static <T> UniquOneServiceException of(ExceptionCode code, String result) {
        return new UniquOneServiceException(code, result);
    }*/

}
