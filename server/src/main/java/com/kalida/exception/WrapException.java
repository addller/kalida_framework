package com.kalida.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class WrapException extends RuntimeException {
    
    private Exception exception;
    private HttpStatus httpStatus;
    private String message;
    private String error;

    public WrapException(Exception exception){
        this(exception, null);
    }

    public WrapException(Exception exception, HttpStatus httpStatus){
        this(exception, httpStatus, null);
    }

    public WrapException(Exception exception, HttpStatus httpStatus, String message){
        this.exception = exception;
        this.error = exception.getClass().getSimpleName();
        this.httpStatus = httpStatus;
        this.message = message;
        if(message == null) this.message = exception.getMessage();
    }
}