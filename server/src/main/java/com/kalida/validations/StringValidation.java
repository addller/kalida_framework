package com.kalida.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = StringValidationConstraint.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StringValidation {
    String message() default "invalid field";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldName();

    boolean nullable() default false;

    boolean empty() default false;

    String regex() default "";

    String regexMessage() default "";

    int min() default 0;

    int max() default Integer.MAX_VALUE;

}