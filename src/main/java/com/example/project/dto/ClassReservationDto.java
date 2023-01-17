package com.example.project.dto;

import com.example.project.enums.EDayOfWeek;
import com.example.project.validators.OnlyAlphaNumericValidator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassReservationDto {
    @NotBlank(message = "The email address must be defined")
    @Email(message = "The email should have an adequate format!")
    private String studentEmail;

    @NotNull(message = "The capacity must be defined")
    @Min(value = 1, message = "Capacity can't be negative")
    private Integer capacity;

    @NotBlank(message = "class name can't be blank!")
    private String className;

    @NotBlank(message = "studio name must contain at least one letter or digit!")
    @OnlyAlphaNumericValidator
    private String studioName;

    @NotNull(message = "The date must be defined")
    private EDayOfWeek dayOfWeek;

    @NotNull(message = "The start hour must be defined")
    private LocalTime startTime;
}