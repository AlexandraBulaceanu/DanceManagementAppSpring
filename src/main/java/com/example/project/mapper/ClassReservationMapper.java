package com.example.project.mapper;

import com.example.project.entity.ClassReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClassReservationMapper {

    private final ReservationTicketMapper reservationTicketMapper;

    @Autowired
    public ClassReservationMapper(ReservationTicketMapper reservationTicketMapper) {
        this.reservationTicketMapper = reservationTicketMapper;
    }

    public ClassReservationDetailsDto mapToClassReservationDetailsDto(ClassReservation classReservation) {
        return ClassReservationDetailsDto.builder()
                .movieName(classReservation.getReservationTickets().get(0).getClassSchedule().getClass().getName())
                .roomName(classReservation.getReservationTickets().get(0).getClassSchedule().getStudio().getName())
                .movieDate(classReservation.getReservationTickets().get(0).getClassSchedule().getId().getDayOfWeek())
                .movieHour(classReservation.getReservationTickets().get(0).getClassSchedule().getId().getStartTime())
                .totalPrice(classReservation.getPrice())
                .tickets(classReservation.getReservationTickets().stream().map(ticketMapper::mapToTicketDto).toList())
                .build();
    }
}