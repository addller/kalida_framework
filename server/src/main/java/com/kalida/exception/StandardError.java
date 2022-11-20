package com.kalida.exception;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

@Getter
public class StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    private long timestamp = System.currentTimeMillis();
    private String message;
    private String path;
    private String error;

    private transient HttpStatus httpStatus;
    private static String WRAP_EXCEPTION = WrapException.class.getSimpleName();
    private static String DOMAIN_EXCEPTION = DomainException.class.getSimpleName();

    public StandardError(Exception exception, HttpServletRequest request){
        this(exception, request, null);
    }
    public StandardError(Exception exception, HttpServletRequest request, HttpStatus httpStatus){
        this(exception, request, httpStatus, null);
    }

    public StandardError(Exception exception, HttpServletRequest request, HttpStatus httpStatus, String message){
        this.error = exception.getClass().getSimpleName();
        this.path = request.getRequestURI();
        if(error.equals(WRAP_EXCEPTION)){
            WrapException wException = (WrapException) exception;
            message = message != null? message : wException.getMessage();
            this.error = wException.getError();
        }
        this.message = message != null? message : exception.getMessage();
        if(error.equals(DOMAIN_EXCEPTION) && httpStatus == null)
            httpStatus = ((DomainException) exception).getHttpStatus();
        this.httpStatus = this.httpStatus != null? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR;            
    }

    ResponseEntity<StandardError> response(){
        return new ResponseEntity<>(this, this.httpStatus);
    }

    static ResponseEntity<StandardError> response(Exception exception, HttpServletRequest request){
        return new StandardError(exception, request).response();
    }
    static ResponseEntity<StandardError> response(Exception exception, HttpServletRequest request, HttpStatus httpStatus){
        return new StandardError(exception, request, httpStatus).response();
    }
    static ResponseEntity<StandardError> response(Exception exception, HttpServletRequest request, HttpStatus httpStatus, String message){
        return new StandardError(exception, request, httpStatus, message).response();
    }


        
}