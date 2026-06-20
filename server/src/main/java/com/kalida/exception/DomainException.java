package com.kalida.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DomainException extends RuntimeException {

    private final HttpStatus httpStatus;
    private Exception originalException;

    public DomainException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
        this.originalException = this;
    }

    public DomainException(String message, HttpStatus httpStatus) {
        this(message, httpStatus, null);
        this.originalException = this;
    }

    public DomainException(String message, HttpStatus httpStatus, Exception originalException) {
        super(message);
        this.httpStatus = httpStatus;
        this.originalException = originalException;
    }

    public static final void validate(boolean validate, String message) {
        validate(validate, message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static final void validate(boolean validate, String message, HttpStatus httpStatus) {
        if (!validate) {
            throw new DomainException(message, httpStatus);
        }
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public static void NotNull(Object object, String fieldName) {
        validate(object != null, fieldName+" can not be null", HttpStatus.CONFLICT);
    }   

}
