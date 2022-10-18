package com.sparos.uniquone.msapostservice.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse<T>{

    private String code;
    private String message;
    private T data;

    public ExceptionResponse(ExceptionCode code,T data){
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    public ExceptionResponse(ExceptionCode code){
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = null;
    }

    public static<T> ExceptionResponse of(ExceptionCode code,T data){
        return new ExceptionResponse(code, data);
    }

    public static ExceptionResponse of(ExceptionCode code){
        return new ExceptionResponse(code);
    }
}
