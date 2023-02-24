package com.kalida.exception;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public final class DomainExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public final ResponseEntity<StandardError> handleDomainException(DomainException domainException, HttpServletRequest request) {
        return StandardError.response(domainException, request);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<StandardError> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        return StandardError.response(ex, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        var standardError = new StandardError(ex, path, status, "validation errors");
        standardError.setErrors(ex.getBindingResult().getFieldErrors());
        return new ResponseEntity<>(standardError, status);
    }
    
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<StandardError> handleException(Exception ex, HttpServletRequest request) {
        return StandardError.response(ex, request);
    }
}
