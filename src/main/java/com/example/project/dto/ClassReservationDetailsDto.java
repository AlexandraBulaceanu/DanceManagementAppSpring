package com.example.project.dto;

import com.example.project.enums.EDayOfWeek;
import com.example.project.validators.OnlyAlphaNumericValidator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassReservationDetailsDto {
    @NotBlank(message = "class name must be defined")
    private String className;

    @NotBlank(message = "studio name must be defined, in alphanumeric")
    @OnlyAlphaNumericValidator
    private String studioName;

    @NotNull(message = "The day of week must be provided")
    private EDayOfWeek dayOfWeek;

    @NotNull(message = "Start time must be defined")
    private LocalTime startTime;

    @NotNull(message = "Price must be defined")
    @Min(value = 1, message = "Price cannot be negative!")
    private Integer price;

    @NotNull(message = "Tickets must be defined")
    @Size(min = 1, message = "At least one reservation ticket must exist")
    private List<ReservationTicketDto> tickets;
}