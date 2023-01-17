package com.example.project.dto;

import com.example.project.validators.OnlyLettersValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDetailsDto {
    @NotNull(message = "Id can't be null")
    private Long id;

    @NotBlank(message = "Instructor name must be defined")
    @OnlyLettersValidator
    private String name;
}