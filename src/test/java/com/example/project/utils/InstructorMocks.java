package com.example.project.utils;

import com.example.project.dto.InstructorDetailsDto;
import com.example.project.dto.InstructorDto;
import com.example.project.entity.Instructor;

import java.util.ArrayList;

public class InstructorMocks {
    public static InstructorDto mockInstructorDto() {
        return InstructorDto.builder()
                .name("Ruxi")
                .build();
    }

    public static InstructorDetailsDto mockInstructorDetailsDto(Long id) {
        return InstructorDetailsDto.builder()
                .id(id)
                .name("Test" + " Instructor "+ id.intValue())
                .build();
    }

    public static InstructorDetailsDto mockInstructorDetailsDto() {
        return InstructorDetailsDto.builder()
                .id(1L)
                .name("Ruxi")
                .build();
    }

    public static Instructor mockInstructor() {
        return Instructor.builder()
                .instructor_id(1L)
                .name("Ruxi")
                .classes(new ArrayList<>())
                .build();
    }
}