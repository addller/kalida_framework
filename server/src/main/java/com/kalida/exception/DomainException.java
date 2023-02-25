package com.kalida.exception;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DomainException extends RuntimeException {

    private final HttpStatus httpStatus;

    public DomainException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public DomainException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
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

}
