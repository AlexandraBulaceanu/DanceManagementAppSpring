package com.example.project.entity;

import com.example.project.enums.EDayOfWeek;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "class_reservations")
public class ClassReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classReservation_id;

    private Integer price;
    private EDayOfWeek dayOfWeek;
    private LocalTime startTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "classReservations_reservationTickets",
            joinColumns = @JoinColumn(name = "classReservation_id"),
            inverseJoinColumns = @JoinColumn(name = "reservationTicket_id")
    )
    private List<ReservationTicket> reservationTickets = new ArrayList<>();
}
