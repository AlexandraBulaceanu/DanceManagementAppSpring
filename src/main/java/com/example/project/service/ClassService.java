package com.example.project.service;

import com.example.project.enums.EDayOfWeek;

import java.time.LocalDate;
import java.util.List;

public interface ClassService {
    List<ClassDetailsDto> searchClasses(String name, LocalDate date, EDayOfWeek genre);

    ClassScheduleDto scheduleClass(ClassScheduleDto classScheduleDto);
}