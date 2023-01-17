package com.example.project.service;

import com.example.project.dto.ClassDetailsDto;
import com.example.project.dto.ClassScheduleDto;
import com.example.project.enums.EDayOfWeek;
import com.example.project.enums.ELevel;

import java.time.LocalDate;
import java.util.List;

public interface ClassService {
    List<ClassDetailsDto> searchClasses(String name, EDayOfWeek dayOfWeek, ELevel level);

    ClassScheduleDto scheduleClass(ClassScheduleDto classScheduleDto);
}