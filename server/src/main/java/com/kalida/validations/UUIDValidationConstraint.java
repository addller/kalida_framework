package com.kalida.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UUIDValidationConstraint implements ConstraintValidator<UUIDValidation, String> {

    private String field;


    @Override
    public void initialize(UUIDValidation constraintAnnotation) {
        this.field = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String text, ConstraintValidatorContext context) {

        var verify = new Verification(field, context);
        if (text == null) {
            verify.addField(" can not be null");
        } else {
            verify.addFieldIfError(text.matches("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}"), "uuid invalid");
        }
        return verify.isValid();
    }

}
