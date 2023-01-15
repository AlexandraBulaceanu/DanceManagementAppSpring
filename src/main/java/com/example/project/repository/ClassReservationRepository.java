package com.example.project.repository;

import com.example.project.entity.ClassReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassReservationRepository extends JpaRepository<ClassReservation, Long> {
}
