package com.example.project.dto;

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
    @NotBlank(message = "class name can't be blank!")
    private String className;

    @NotBlank(message = "studio name must contain at least one letter or digit!")
    @OnlyAlphaNumericValidator
    private String studioName;

    @NotNull(message = "The date must be provided!")
    @FutureOrPresent(message = "You can't select a past date")
    private LocalDate classDate;

    @NotNull(message = "The hour must be provided!")
    private LocalTime classHour;

    @NotNull(message = "Purchase date must be provided!")
    private LocalDate purchaseDate;

    @NotNull(message = "Purchase time must be provided!")
    private LocalTime purchaseTime;

    @NotNull(message = "Total price must be provided!")
    @Min(value = 1, message = "Price can't be negative!")
    private Integer totalPrice;

    @NotNull(message = "Tickets must be provided!")
    @Size(min = 1, message = "There should be at least one ticket!")
    private List<ReservationTicketDto> tickets;
}