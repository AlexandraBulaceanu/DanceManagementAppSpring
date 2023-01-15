package com.example.project.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OnlyLettersDefinition implements ConstraintValidator<OnlyLettersValidator, String>{
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null) {
            return false;
        }
        return value.matches("^[a-zA-Z -]*$");
    }
}