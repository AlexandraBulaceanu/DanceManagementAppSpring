package com.example.project.entity;

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
@Table(name = "studios")
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studio_id;

    @Column(unique = true)
    private String name;

    private Integer capacity;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClassSchedule> classSchedules = new ArrayList<>();
}
