package com.example.project.entity;

import com.example.project.enums.ESubscription;
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
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long student_id;

    @Column(unique = true)
    private String name;
    private String email;
    private Integer age;
    private ESubscription subscription_type;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "students_classReservations",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "classReservation_id")
    )
    private List<ClassReservation> classReservations = new ArrayList<>();

}
