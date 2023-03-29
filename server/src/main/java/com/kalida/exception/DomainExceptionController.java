package com.kalida.exception;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

    @ExceptionHandler(DomainException.class)
    public final ResponseEntity<StandardError> handleDomainException(DomainException domainException,
            HttpServletRequest request) {
        return StandardError.response(domainException, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<StandardError> handleAccessDeniedException(AccessDeniedException ex,
            HttpServletRequest request) {
        return StandardError.response(ex, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        StandardError standardError = new StandardError(ex, path, status, "validation errors")
                .setErrors(errors);
        return new ResponseEntity<>(standardError, standardError.getHttpStatus());
    }
    
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<StandardError> handleException(Exception ex, HttpServletRequest request) {
        return StandardError.response(ex, request);
    }
}
