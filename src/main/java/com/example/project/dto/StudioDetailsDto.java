package com.example.project.dto;

import com.example.project.validators.OnlyAlphaNumericValidator;
import jakarta.validation.constraints.Min;
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
public class StudioDetailsDto {
    @NotNull(message = "Id can't be null!")
    private Long id;

    @NotBlank(message = "studio name must contain at least one letter or digit!")
    @OnlyAlphaNumericValidator
    private String name;

    @NotNull
    @Min(value = 1, message = "Capacity cannot be negative")
    private Integer capacity;

}