package com.kalida.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public final class DomainExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public final ResponseEntity<StandardError> handleDomainException(DomainException domainException, HttpServletRequest request) {
        return StandardError.response(domainException, request);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<StandardError> handleDomainException(AccessDeniedException ex, HttpServletRequest request) {
        return StandardError.response(ex, request);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<StandardError> handleDomainException(Exception ex, HttpServletRequest request) {
        return StandardError.response(ex, request);
    }
}
