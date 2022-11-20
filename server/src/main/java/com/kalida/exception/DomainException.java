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

    public static void keys(JSONObject data, String... keys) {
        for (String key : keys) {
            if (!data.containsKey(key)) {
                throw new DomainException("Key [" + key + "] not found on json");
            }
        }
    }

    public static void areNumberKeys(JSONObject data, String... keys) {
        keys(data, keys);
        String tKey = null;
        try {
            for (String key : keys) {
                tKey = key;
                data.getAsNumber(tKey);
            }
        } catch (Exception e) {
            throw new DomainException("The key [" + tKey + "] is not a number: " + data.getAsString(tKey), HttpStatus.BAD_REQUEST);
        }
    }
    

    public static void allJSONKeys(JSONObject data, String[] numberKeys, String... stringKeys) {
        keys(data, stringKeys);
        areNumberKeys(data, numberKeys);
    }

    public static void propagate(String error) {
        propagate(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static void propagate(String error, HttpStatus httpStatus) {
        throw new DomainException(error, httpStatus);
    }

}
