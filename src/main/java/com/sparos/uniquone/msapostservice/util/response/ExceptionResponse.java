package com.sparos.uniquone.msapostservice.util.response;

import lombok.Setter;

@Setter
public class ExceptionResponse <T>{

    private String code;
    private String message;
    private T data;

    public ExceptionResponse (ExceptionCode code, T data){
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }
    public ExceptionResponse (ExceptionCode code){
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public static ExceptionResponse of(ExceptionCode code){
        return new ExceptionResponse(code);
    }

    public static<T> ExceptionResponse of(ExceptionCode code, T data){
        return new ExceptionResponse(code,data);
    }

}
