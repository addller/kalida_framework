package com.kalida.exception;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author ie
 */
@ControllerAdvice
@RestController
public final class DomainExceptionController extends ResponseEntityExceptionHandler {

    @Autowired
    private StandardError standardError;

    @ExceptionHandler(DomainException.class)
    public final ResponseEntity<StandardError> handleDomainException(DomainException domainException,
            HttpServletRequest request) {
        return standardError.response(domainException, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<StandardError> handleAccessDeniedException(AccessDeniedException ex,
            HttpServletRequest request) {
        return standardError.response(ex, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        standardError
            .setErrors(errors)
            .response(ex, path, status, "validation errors");
        return new ResponseEntity<>(standardError, status);
    }
    
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<StandardError> handleException(Exception ex, HttpServletRequest request) {
        return standardError.response(ex, request);
    }
}
