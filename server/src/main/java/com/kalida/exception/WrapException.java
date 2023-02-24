package com.kalida.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class WrapException extends RuntimeException {
    
    private final Exception exception;
    private final HttpStatus httpStatus;
    private String message;

    public WrapException(Exception exception){
        this(exception, null);
    }

    public WrapException(Exception exception, HttpStatus httpStatus){
        this(exception, httpStatus, null);
    }

    public WrapException(Exception exception, HttpStatus httpStatus, String message){
        this.exception = exception;
        this.httpStatus = httpStatus;
        this.message = message;
        if(message == null) this.message = exception.getMessage();
    }

    public String getSimpleExceptionName(){
        return exception.getClass().getSimpleName();
    }
}