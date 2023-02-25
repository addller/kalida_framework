package com.kalida.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;


import lombok.AccessLevel;
import lombok.Getter;

@Component
@Getter
public class StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private long timestamp = System.currentTimeMillis();

    @Value("${security.hide.exception}")
    private boolean hideException;

    private String path;
    private HttpStatus httpStatus;
    private String exception;
    private String message;
    private Map<String, List<String>> errors = new HashMap<>();

    @Getter(value = AccessLevel.NONE)
    private static final String DOMAIN_EXCEPTION = DomainException.class.getSimpleName();

    public ResponseEntity<StandardError> response(Exception exception, HttpServletRequest request) {
        return response(exception, request, null);
    }

    public ResponseEntity<StandardError> response(Exception exception, HttpServletRequest request, HttpStatus httpStatus) {
        return response(exception, request.getRequestURI(), httpStatus, null);
    }

    public ResponseEntity<StandardError> response(Exception exception, HttpServletRequest request, HttpStatus httpStatus, String message) {
        return response(exception, request.getRequestURI(), httpStatus, message);
    }

    public ResponseEntity<StandardError> response(Exception exception, String requestedURI, HttpStatus httpStatus, String message) {
        this.exception = exception.getClass().getSimpleName();
        this.path = requestedURI;
        this.message = message != null ? message : exception.getMessage();
        
        if (this.exception.equals(DOMAIN_EXCEPTION) && httpStatus == null)
            httpStatus = ((DomainException) exception).getHttpStatus();
            
        if(hideException) 
            this.exception = "exception";

        this.httpStatus = httpStatus != null ? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(this, this.httpStatus);
    }

    public StandardError setErrors(List<FieldError> fieldErrors){
        fieldErrors.forEach(error -> {
            errors.putIfAbsent(error.getField(), new ArrayList<>());
            errors.get(error.getField()).add(error.getDefaultMessage());
        });
        return this;
    }
}