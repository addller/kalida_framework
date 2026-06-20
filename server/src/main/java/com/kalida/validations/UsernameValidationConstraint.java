package com.kalida.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidationConstraint implements ConstraintValidator<UsernameValidation, String> {

    private int min;
    private int max;

    @Override
    public void initialize(UsernameValidation constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        var verification = new Verification("username",context);
        if(
            verification.notBlank(username) 
            && verification.minMax(username, min, max)
            && !username.matches("(?i)[a-z]+[0-9a-z_\\.]*")
        ){
            verification.pattern(username, "(?i)[a-z].*", "must start with letter");
            verification.addFieldIfError(!username.replaceAll("[_\\.]", "").matches(".*\\W.*"),"accepts only dot and underscore as special characters");
        }
        if(username != null){
            verification.addFieldIfError(!username.matches(".*[_\\.]{2,}.*"), "username must not contain two consecutive underscores or dots");
        }
        
        return verification.isValid();
    }
}
