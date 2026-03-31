package com.kalida.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MinMaxIntValidationConstraint implements ConstraintValidator<MinMaxIntValidation, Integer> {

    private String field;

    private boolean nullable;

    private long min;

    private long max;

    @Override
    public void initialize(MinMaxIntValidation constraintAnnotation) {
        this.field = constraintAnnotation.fieldName();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        var verify = new Verification(field, context);
        if (value == null) {
            verify.addFieldIfError(this.nullable, "can not be null");
        } else {
            verify.addFieldIfError(value >= min, "below min acceptable: "+min);
            verify.addFieldIfError(value <= max, "above max acceptable: "+max);
        }
        return verify.isValid();
    }

}
