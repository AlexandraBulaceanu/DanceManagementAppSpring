package com.example.project.dto;

import com.example.project.validators.OnlyLettersValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    @OnlyLettersValidator
    private String name;

    @NotBlank(message = "The email must be defined")
    @Email(message = "The format of the email should be valid")
    private String email;
}