package com.example.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservationTickets")
public class ReservationTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationTicket_id;

    private Long finalPrice;
    private LocalDate datePaid;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClassSchedule classSchedule;
}

