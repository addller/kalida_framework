package com.kalida.exception;


import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author ie
 */
@ControllerAdvice
public final class DomainExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public final ResponseEntity<StandardError> handleDomainException(DomainException domainException,
            HttpServletRequest request) {
        return StandardError.response(domainException.getOriginalException(), request, domainException.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<StandardError> handleAccessDeniedException(AccessDeniedException ex,
            HttpServletRequest request) {
        return StandardError.response(ex, request);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<StandardError> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
            HttpServletRequest request) {
            System.out.println(ex.getMessage());
        return StandardError.response(ex, request, HttpStatus.BAD_REQUEST, "Exception Handler: see log for details");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        StandardError standardError = new StandardError(ex, path, status, "validation errors").setErrors(errors);
        return new ResponseEntity<>(standardError, standardError.getHttpStatus());
    }
    
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<StandardError> handleException(Exception ex, HttpServletRequest request) {
        return StandardError.response(ex, request);
    }
}
