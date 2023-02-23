package com.kalida.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    private long timestamp = System.currentTimeMillis();
    private String message;
    private String path;
    private String exception;
    private List<String> errorsList = new ArrayList<>();
    private transient HttpStatus httpStatus;

    @Getter(value = AccessLevel.NONE)
    private static final String WRAP_EXCEPTION = WrapException.class.getSimpleName();
    
    @Getter(value = AccessLevel.NONE)
    private static final String DOMAIN_EXCEPTION = DomainException.class.getSimpleName();

    public StandardError(Exception exception, HttpServletRequest request){
        this(exception, request, null);
    }
    public StandardError(Exception exception, HttpServletRequest request, HttpStatus httpStatus){
        this(exception, request.getRequestURI(), httpStatus, null);
    }

    public StandardError(Exception exception, HttpServletRequest request, HttpStatus httpStatus, String message){
        this(exception, request.getRequestURI(), httpStatus, message);
    }

    public StandardError(Exception exception, String requestedURI, HttpStatus httpStatus, String message){
        this.exception = exception.getClass().getSimpleName();
        this.path = requestedURI;
        if(exception.equals(WRAP_EXCEPTION)){
            WrapException wException = (WrapException) exception;
            message = message != null? message : wException.getMessage();
            this.exception = wException.getError();
        }
        this.message = message != null? message : exception.getMessage();
        if(exception.equals(DOMAIN_EXCEPTION) && httpStatus == null)
            httpStatus = ((DomainException) exception).getHttpStatus();
        this.httpStatus = httpStatus != null? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR;            
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
    
    public void addError(String ... errors){
        errorsList.addAll(Arrays.asList(errors));
    }

    public void addAllErrors(List<String> errors){
        errorsList.addAll(errors);
    }
}