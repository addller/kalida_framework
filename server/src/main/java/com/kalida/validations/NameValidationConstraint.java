package com.kalida.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidationConstraint implements ConstraintValidator<NameValidation, String> {

    private String fieldName;
    private int min;
    private int max;

    @Override
    public void initialize(NameValidation constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {

        var verification = new Verification(fieldName, context);
        if(
            verification.notBlank(name)
            && verification.minMax(name, min, max)
            && !name.matches("[a-z]+[a-z ]*")
        ){
            verification.pattern(name, "(?i)^[a-z].*", "must start with letter");
            verification.addFieldIfError(!name.matches(".*[^a-zA-Z ].*"), "accepts only letter and space");
        }
        return verification.isValid();
    }


}
