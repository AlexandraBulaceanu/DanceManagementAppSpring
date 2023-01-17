package com.example.project.mapper;

import com.example.project.dto.ReservationTicketDto;
import com.example.project.entity.ReservationTicket;
import org.springframework.stereotype.Component;

@Component
public class ReservationTicketMapper {

    public ReservationTicketDto mapToReservationTicketDto(ReservationTicket reservationTicket) {
        return ReservationTicketDto.builder()
                .build();
    }
}
