package com.example.project.mapper;


import com.example.project.entity.Instructor;
import org.springframework.stereotype.Component;

@Component
public class InstructorMapper {

    public InstructorDto mapToInstructorDto(Instructor instructor) {
        return InstructorDto.builder()
                .name(instructor.getName())
                .build();
    }

    public Instructor mapToInstructor(InstructorDto instructorDto) {
        return Instructor.builder()
                .name(instructorDto.getName())
                .build();
    }

    public InstructorDetailsDto mapToInstructorDetailsDto(Instructor instructor) {
        return InstructorDetailsDto.builder()
                .instructor_id(instructor.getInstructor_id())
                .name(instructor.getName())
                .build();
    }
}
