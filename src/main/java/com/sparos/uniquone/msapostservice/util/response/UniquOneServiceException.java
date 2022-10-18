package com.sparos.uniquone.msapostservice.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class UniquOneServiceException extends RuntimeException {

    private ExceptionCode exceptionCode;
    private String code;
    private String message;
    private String result;
    private String data;

    public UniquOneServiceException(ExceptionCode code, String result){
        this.code = code.getCode();
        this.message = code.getMessage();
        this.result = result;
        this.data = null;
    }

    public static<T> UniquOneServiceException of(ExceptionCode code, String result){
        return new UniquOneServiceException(code, result);
    }

}
