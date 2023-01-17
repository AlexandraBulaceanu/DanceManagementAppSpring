package com.example.project.dto;

import com.example.project.enums.EDayOfWeek;
import com.example.project.validators.OnlyAlphaNumericValidator;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassScheduleDto {
    @NotBlank(message = "Class name must be defined")
    private String className;

    @NotBlank(message = "Studio name must be defined")
    @OnlyAlphaNumericValidator
    private String studioName;

    @NotNull(message = "Day of week must be defined")
    private EDayOfWeek dayOfWeek;

    @NotNull(message = "Start time must be provided")
    private LocalTime startTime;

    @NotNull(message = "Class price must be defined")
    @Min(value = 1, message = "Class price can't be negative")
    private Integer price;
}