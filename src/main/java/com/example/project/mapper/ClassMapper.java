package com.example.project.mapper;

import com.example.project.dto.ClassDetailsDto;
import com.example.project.dto.ClassDto;
import com.example.project.dto.InstructorDto;
import com.example.project.dto.NewClassDto;
import com.example.project.entity.Instructor;
import com.example.project.entity.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClassMapper {

    private final InstructorMapper instructorMapper;

    @Autowired
    public ClassMapper(InstructorMapper instructorMapper) {
        this.instructorMapper = instructorMapper;
    }

    public ClassDto mapToClassDto(NewClassDto cls, List<Instructor> instructors) {
        return ClassDto.builder()
                .name(cls.getName())
                .description(cls.getDescription())
                .hoursDuration(cls.getHoursDuration())
                .level(cls.getLevel())
                .instructors(instructors)
                .build();
    }

    public Class mapToClass(ClassDto classDto) {
        return Class.builder()
                .name(classDto.getName())
                .description(classDto.getDescription())
                .hoursDuration(classDto.getHoursDuration())
                .level(classDto.getLevel())
                .instructors(classDto.getInstructors())
                .build();
    }

    public ClassDetailsDto mapToClassDetailsDto(Class cls) {
        return ClassDetailsDto.builder()
                .name(cls.getName())
                .description(cls.getDescription())
                .hoursDuration(cls.getHoursDuration())
                .level(cls.getLevel())
                .instructors(cls.getInstructors().stream().map(instructorMapper::mapToInstructorDto).collect(Collectors.toList()))
                .scheduleDetails(
                        cls.getClassSchedules().stream().map(classSchedule ->
                                ClassDetailsDto.ClassScheduleDetails.builder()
                                        .dayOfWeek(classSchedule.getId().getDayOfWeek())
                                        .startTime(classSchedule.getId().getStartTime())
                                        .classPrice(classSchedule.getClassPrice())
                                        .studioName(classSchedule.getStudio().getName())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .build();
    }
}
