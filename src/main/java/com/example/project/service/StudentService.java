package com.example.project.service;

import com.example.project.dto.StudentDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> searchStudents(String searchParam);
}