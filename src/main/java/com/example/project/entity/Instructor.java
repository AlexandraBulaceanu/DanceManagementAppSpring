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
@Table(name = "instructors")
public class Instructor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long instructor_id;

        @Column(unique = true)
        private String name;

        @ManyToMany(mappedBy = "instructors")
        private List<Class> classes;
}
