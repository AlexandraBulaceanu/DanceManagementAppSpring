package com.example.project.repository;

import com.example.project.entity.ClassSchedule;
import com.example.project.helper.ClassScheduleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, ClassScheduleId> {
}
