package com.example.project.mapper;

import com.example.project.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentDto mapToStudentDto(Student student) {
        return StudentDto.builder()
                .name(student.getName())
                .email(student.getEmail())
                .build();
    }

    public Student mapToStudent(StudentDto studentDto) {
        return Student.builder()
                .name(studentDto.getName())
                .email(studentDto.getEmail())
                .build();
    }
}