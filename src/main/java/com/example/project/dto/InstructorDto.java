package com.example.project.dto;

import com.example.project.validators.OnlyLettersValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDto {

    @NotBlank(message = "Instructor name must be defined")
    @OnlyLettersValidator
    private String name;
}