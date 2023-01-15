package com.example.project.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {OnlyLettersDefinition.class})
public @interface OnlyLettersValidator {
    String message() default "Only letters!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}