package com.example.project.mapper;

import com.example.project.entity.ReservationTicket;
import org.springframework.stereotype.Component;

@Component
public class ReservationTicketMapper {

    public ReservationTicketDto mapToReservationTicketDto(ReservationTicket reservationTicket) {
        return ReservationTicketDto.builder()
                .datePaid(reservationTicket.getDatePaid())
                .finalPrice(reservationTicket.getFinalPrice())
                .build();
    }
}
