package com.kalida.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NicknameValidationConstraint implements ConstraintValidator<NicknameValidation, String> {

    private int min;
    private int max;
    @Override
    public void initialize(NicknameValidation constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }
    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {

        var verification = new Verification("nickname", context);
        if(
            verification.notBlank(nickname) 
            && verification.minMax(nickname, min, max)
            && !nickname.matches("(?i)[a-z]+[0-9a-z_\\.]*")
        ){
            verification.pattern(nickname, "(?i)[a-z].*", "must start with letter");
            verification.addFieldIfError(!nickname.replaceAll("[_\\.]", "").matches(".*\\W.*"), "accepts only dot and underscore as special characters");
        }
        if(nickname != null){
            verification.addFieldIfError(!nickname.matches(".*[_\\.]{2,}.*"), "nickname must not contain two consecutive underscores or dots");
        }

        return verification.isValid();
    }


}
