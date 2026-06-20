package com.kalida.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StringValidationConstraint implements ConstraintValidator<StringValidation, String> {

    private String field;

    private boolean nullable;

    private boolean empty;

    private String regex;

    private String regexMessage;

    private int min;

    private int max;

    @Override
    public void initialize(StringValidation constraintAnnotation) {
        this.field = constraintAnnotation.fieldName();
        this.nullable = constraintAnnotation.nullable();
        this.empty = constraintAnnotation.empty();
        this.regex = constraintAnnotation.regex();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.regexMessage = constraintAnnotation.regexMessage();
    }

    @Override
    public boolean isValid(String text, ConstraintValidatorContext context) {

        var verify = new Verification(field, context);
        if (text == null) {
            verify.addFieldIfError(this.nullable, "can not be null");
        } else {
            verify.minMax(text, min, max);

            if (text.isEmpty())
                verify.addFieldIfError(this.empty, "can not be empty");

            if (!regex.isEmpty()) {
                String msg = regexMessage.isEmpty() ? "invalid by regex" : regexMessage;
                verify.pattern(text, regex, msg);
            }
        }
        return verify.isValid();
    }

}
