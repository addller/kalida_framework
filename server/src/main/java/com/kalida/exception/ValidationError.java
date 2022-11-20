package com.kalida.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

public class ValidationError extends StandardError {

    private List<FieldMessage> fieldMessages = new ArrayList<>();

    public ValidationError(Exception exception, HttpServletRequest request){
        this(exception, request, null);
    }
    public ValidationError(Exception exception, HttpServletRequest request, String message){
        super(exception, request, HttpStatus.BAD_REQUEST, message);
    }

    public List<FieldMessage> getErrors(){
        return fieldMessages;
    }

    public void addError(String field, String message){
        fieldMessages.add(new FieldMessage(field, message));
    }

}
