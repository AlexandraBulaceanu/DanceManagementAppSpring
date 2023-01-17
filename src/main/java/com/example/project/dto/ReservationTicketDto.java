package com.example.project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationTicketDto {
    @NotNull(message = "The price must be defined")
    @Min(value = 1, message = "Price cannot be negative")
    private Long finalPrice;


}