package com.example.project.service;

import com.example.project.dto.InstructorDetailsDto;
import com.example.project.dto.InstructorDto;

import java.util.List;

public interface InstructorService {
    List<InstructorDetailsDto> getInstructors();

    InstructorDto editInstructor(Long id, InstructorDto instructorDto);

    InstructorDto deleteInstructor(Long id);
}