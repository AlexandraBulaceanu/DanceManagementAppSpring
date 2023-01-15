package com.example.project.entity;

import com.example.project.enums.ELevel;
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
@Table(name = "classes")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long class_id;

    @Column(unique = true)
    private String name;
    private String description;
    private Integer hoursDuration;
    private Integer ageLimit;
    private ELevel level;

    @ManyToMany
    @JoinTable(
            name = "instructors_classes",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id")
    )
    private List<Instructor> instructors = new ArrayList<>();

    @OneToMany(mappedBy = "cls", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClassSchedule> classSchedules = new ArrayList<>();
}
