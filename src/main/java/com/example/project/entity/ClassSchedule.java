package com.example.project.entity;

import com.example.project.helper.ClassScheduleId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classes_schedule")
public class ClassSchedule {

    @EmbeddedId
    private ClassScheduleId id;

    private Integer classPrice;

    @MapsId("classId")
    @ManyToOne
    private Class cls;

    @MapsId("studioId")
    @ManyToOne
    private Studio studio;

    @OneToMany(mappedBy = "classSchedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ReservationTicket> reservationTickets = new ArrayList<>();
}
