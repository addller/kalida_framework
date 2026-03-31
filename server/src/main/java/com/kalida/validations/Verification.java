package com.kalida.validations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kalida.exception.FieldMessage;

import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class Verification {

    private final ConstraintValidatorContext context;

    @Getter(value = AccessLevel.NONE)
    private boolean compiled;

    private String fieldName;

    public Verification(String fieldName, ConstraintValidatorContext context) {
        this.fieldName = fieldName;
        this.context = context;
    }

    private List<FieldMessage> fieldMessages = new ArrayList<>();

    public Verification addField(String message) {
        fieldMessages.add(new FieldMessage(fieldName, message));
        return this;
    }

    public Verification addFieldIfError(boolean valid, String message) {
        if (!valid)
            fieldMessages.add(new FieldMessage(fieldName, message));
        return this;
    }

    public Verification compileFieldMessages() {
        return compileFieldMessages(false);
    }

    public Verification compileFieldMessages(boolean addPropertyNode) {
        for (var fieldMessage : fieldMessages) {
            context.disableDefaultConstraintViolation();
            var contraintBuilder = context.buildConstraintViolationWithTemplate(fieldMessage.getMessage());
            if (addPropertyNode)
                contraintBuilder.addPropertyNode(fieldMessage.getName());
            contraintBuilder.addConstraintViolation();
        }
        compiled = true;
        return this;
    }

    public boolean isValid() {
        if (!compiled)
            compileFieldMessages();
        return fieldMessages.isEmpty();
    }

    public boolean notNull(Object field) {
        boolean valid = field != null;
        addFieldIfError(valid, "can not be null");
        return valid;
    }

    public boolean notEmpty(String text) {
        boolean valid = !text.isEmpty();
        addFieldIfError(valid, "can not be empty");
        return valid;
    }

    public boolean notBlank(String text) {
        return notNull(text) && notEmpty(text);
    }

    public boolean pattern(String text, String regex) {
        return pattern(text, regex, "pattern invalid");
    }

    public boolean pattern(String text, String regex, String message) {
        boolean valid = text.matches(regex);
        addFieldIfError(valid, message);
        return valid;
    }

    public boolean min(String text, long min) {
        boolean valid = text.length() >= min;
        addFieldIfError(valid, "has few characters");
        return valid;
    }

    public boolean max(String text, long max) {
        boolean valid = text.length() <= max;
        addFieldIfError(valid, "has many characters");
        return valid;
    }

    public boolean minMax(String text, long min, long max) {
        return min(text, min) && max(text, max);
    }

    public boolean before(Date request, Date reference) {
        LocalDate dateRequest = LocalDate.ofEpochDay(request.getTime()),
                dateReference = LocalDate.ofEpochDay(reference.getTime());
        boolean valid = dateRequest.isBefore(dateReference);
        addFieldIfError(valid, "in request is not before reference date: " + reference);
        return valid;
    }

    public boolean after(Date request, Date reference) {
        LocalDate dateRequest = LocalDate.ofEpochDay(request.getTime()),
                dateReference = LocalDate.ofEpochDay(reference.getTime());
        boolean valid = dateRequest.isAfter(dateReference);
        addFieldIfError(valid, "in request is not after reference date: " + reference);
        return valid;
    }

    public boolean beforeOrEqual(Date request, Date reference) {
        LocalDate dateRequest = LocalDate.ofEpochDay(request.getTime()),
                dateReference = LocalDate.ofEpochDay(reference.getTime());
        boolean valid = dateRequest.isBefore(dateReference) || dateRequest.isEqual(dateReference);
        addFieldIfError(valid, "in request is not before or equal reference date " + reference);
        return valid;
    }

    public boolean afterOrEqual(Date request, Date reference) {
        LocalDate dateRequest = LocalDate.ofEpochDay(request.getTime()),
                dateReference = LocalDate.ofEpochDay(reference.getTime());
        boolean valid = dateRequest.isAfter(dateReference) || dateRequest.isEqual(dateReference);
        addFieldIfError(valid, "in request is not after or equal reference date " + reference);
        return valid;
    }

}
