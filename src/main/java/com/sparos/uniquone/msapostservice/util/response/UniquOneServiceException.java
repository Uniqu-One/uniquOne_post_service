package com.sparos.uniquone.msapostservice.util.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UniquOneServiceException extends RuntimeException {

    private ExceptionCode exceptionCode;
    private String data;
    private HttpStatus status;

    public UniquOneServiceException(ExceptionCode code, HttpStatus status) {
        this.exceptionCode = code;
        this.status = status;
        this.data = null;
    }

    /*public static <T> UniquOneServiceException of(ExceptionCode code, String result) {
        return new UniquOneServiceException(code, result);
    }*/

}
