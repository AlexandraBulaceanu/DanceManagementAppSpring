package com.example.project.mapper;

import com.example.project.dto.ClassReservationDetailsDto;
import com.example.project.entity.ClassReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClassReservationMapper {

    private final ReservationTicketMapper reservationTicketMapper;

    @Autowired
    public ClassReservationMapper(ReservationTicketMapper reservationTicketMapper) {
        this.reservationTicketMapper = reservationTicketMapper;
    }

    public ClassReservationDetailsDto mapToClassReservationDetailsDto(ClassReservation classReservation) {
        return ClassReservationDetailsDto.builder()
                .className(classReservation.getReservationTickets().get(0).getClassSchedule().getClass().getName())
                .studioName(classReservation.getReservationTickets().get(0).getClassSchedule().getStudio().getName())
                .startTime(classReservation.getReservationTickets().get(0).getClassSchedule().getId().getStartTime())
                .price(classReservation.getPrice())
                .tickets(classReservation.getReservationTickets().stream().map(reservationTicketMapper::mapToReservationTicketDto).collect(Collectors.toList()))
                .build();
    }
}