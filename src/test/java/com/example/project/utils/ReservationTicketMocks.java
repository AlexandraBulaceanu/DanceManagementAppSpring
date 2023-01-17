package com.example.project.utils;

import com.example.project.dto.ReservationTicketDto;
import com.example.project.entity.ClassSchedule;
import com.example.project.entity.ReservationTicket;

import java.time.LocalDate;

public class ReservationTicketMocks {
    public static ReservationTicketDto mockReservationTicketDto() {
        return ReservationTicketDto.builder()
                .finalPrice(100L)
                .build();
    }

    public static ReservationTicket mockReservationTicket(ClassSchedule classSchedule) {
        return ReservationTicket.builder()
                .finalPrice(100L)
                .datePaid(LocalDate.now())
                .classSchedule(classSchedule)
                .build();
    }
}