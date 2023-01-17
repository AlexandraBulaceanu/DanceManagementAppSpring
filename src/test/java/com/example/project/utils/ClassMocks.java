package com.example.project.utils;

import com.example.project.dto.ClassDetailsDto;
import com.example.project.dto.ClassDto;
import com.example.project.dto.ClassFiltersDto;
import com.example.project.dto.NewClassDto;
import com.example.project.entity.Instructor;
import com.example.project.entity.Class;
import com.example.project.enums.EDayOfWeek;
import com.example.project.enums.ELevel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassMocks {
    public static NewClassDto mockNewClassDto() {
        return NewClassDto.builder()
                .name("Heels")
                .description("Cool dancing")
                .level(ELevel.BEGINNER)
                .hoursDuration(2)
                .instructorsIds(Arrays.asList(1L))
                .build();
    }

    public static ClassDetailsDto mockClassDetailsDto() {
        return ClassDetailsDto.builder()
                .name("Heels")
                .description("Cool dancing")
                .level(ELevel.BEGINNER)
                .hoursDuration(2)
                .instructors(Arrays.asList(InstructorMocks.mockInstructorDto()))
                .scheduleDetails(Arrays.asList(
                        ClassDetailsDto.ClassScheduleDetails.builder()
                                .studioName("Studio1")
                                .dayOfWeek(EDayOfWeek.TUESDAY)
                                .startTime(LocalTime.of(21,0,0))
                                .classPrice(100)
                                .build()
                ))
                .build();
    }

    public static ClassFiltersDto mockClasssFiltersDto() {
        return ClassFiltersDto.builder()
                .name("Heels")
                .dayOfWeek(EDayOfWeek.TUESDAY)
                .level(ELevel.BEGINNER)
                .build();
    }

    public static Class mockClass() {
        return Class.builder()
                .class_id(1L)
                .name("Heels")
                .description("Cool dancing")
                .hoursDuration(2)
                .level(ELevel.BEGINNER)
                .instructors(new ArrayList<>())
                .classSchedules(new ArrayList<>())
                .build();
    }

    public static ClassDto mockClassDto(List<Instructor> instructors) {
        return ClassDto.builder()
                .name("Heels")
                .description("Cool dancing")
                .hoursDuration(2)
                .level(ELevel.BEGINNER)
                .instructors(instructors)
                .build();
    }
}