package com.kalida.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidationConstraint implements ConstraintValidator<PasswordValidation, String> {

    private short min;
    private short max;

    @Override
    public void initialize(PasswordValidation constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        var verify = new Verification("password", context);
        if (verify.notBlank(password)) {
            verify.pattern(password, ".*\\d.*", "must have digit");
            verify.pattern(password, ".*[a-z].*", "must have lower case letter");
            verify.pattern(password, ".*[A-Z].*", "must have upper case letter");
            verify.pattern(password, ".*\\W.*", "must have special character");
            verify.minMax(password, min, max);
        }
        return verify.isValid();
    }

}