package com.example.project.service.implementations;

import com.example.project.constants.Constants;
import com.example.project.dto.StudentDto;
import com.example.project.entity.Student;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.exceptions.NotUniqueException;
import com.example.project.mapper.StudentMapper;
import com.example.project.repository.StudentRepository;
import com.example.project.service.CommonService;
import com.example.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService, CommonService<StudentDto, StudentDto> {
    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentDto add(StudentDto studentDto) {
        Optional<Student> optionalStudent = studentRepository.findByEmail(studentDto.getEmail());
        if(optionalStudent.isPresent()) {
            throw new NotUniqueException(String.format(Constants.NOT_UNIQUE_ERROR, "email"));
        }
        return studentMapper.mapToStudentDto(studentRepository.save(studentMapper.mapToStudent(studentDto)));
    }

    @Override
    public List<StudentDto> searchStudents(String searchParam) {
        if(searchParam == null) {
            return studentRepository.findAll().stream().map(studentMapper::mapToStudentDto).collect(Collectors.toList());
        }

        searchParam = searchParam.toLowerCase();
        List<Student> students = studentRepository.searchByName(searchParam);
        if(students.isEmpty()) {
            throw new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, searchParam));
        }
        return students.stream().map(studentMapper::mapToStudentDto).collect(Collectors.toList());
    }
}