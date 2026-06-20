package com.kalida.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UsernameValidationConstraint.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameValidation {
    String message() default "invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int min() default 1;
    int max() default 60;
    String fieldName() default "username";
}