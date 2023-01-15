package com.example.project.service;

import java.util.List;

public interface StudentService {
    List<StudentDto> searchStudents(String searchParam);
}