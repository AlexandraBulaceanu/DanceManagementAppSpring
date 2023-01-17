package com.example.project.utils;

import com.example.project.dto.ClassReservationDetailsDto;
import com.example.project.dto.ClassReservationDto;
import com.example.project.entity.ClassReservation;
import com.example.project.entity.ReservationTicket;
import com.example.project.enums.EDayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class ClassReservationMocks {
    public static ClassReservationDto mockClassReservationDto() {
        return ClassReservationDto.builder()
                .studentEmail("alexandra@gmail.com")
                .className("Heels")
                .studioName("Studio1")
                .dayOfWeek(EDayOfWeek.TUESDAY)
                .startTime(LocalTime.of(21,0,0))
                .build();
    }

    public static ClassReservationDetailsDto mockClassReservationDetailsDto() {
        return ClassReservationDetailsDto.builder()
                .className("Heels")
                .studioName("Studio1")
                .dayOfWeek(EDayOfWeek.TUESDAY)
                .startTime(LocalTime.of(21,0,0))
                .tickets(Arrays.asList(ReservationTicketMocks.mockReservationTicketDto()))
                .build();
    }

    public static ClassReservation mockClassReservation(Integer price, List<ReservationTicket> tickets) {
        return ClassReservation.builder()
                .dayOfWeek(EDayOfWeek.TUESDAY)
                .startTime(LocalTime.of(21, 0, 0))
                .price(price)
                .reservationTickets(tickets)
                .build();
    }
}