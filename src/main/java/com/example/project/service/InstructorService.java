package com.example.project.service;

import java.util.List;

public interface InstructorService {
    List<InstructorDetailsDto> getAInstructors();

    InstructorDto editInstructor(Long id, InstructorDto instructorDto);

    InstructorDto deleteInstructor(Long id);
}