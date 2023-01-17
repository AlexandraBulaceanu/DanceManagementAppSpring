package com.example.project.utils;

import com.example.project.dto.StudentDto;
import com.example.project.entity.Student;

public class StudentMocks {
    public static StudentDto mockStudentDto() {
        return StudentDto.builder()
                .name("Alexandra Bulaceanu")
                .email("alexandra@gmail.com")
                .build();
    }

    public static Student mockStudent() {
        return Student.builder()
                .student_id(1L)
                .name("Alexandra Bulaceanu")
                .email("alexandra@gmail.com")
                .build();
    }
}