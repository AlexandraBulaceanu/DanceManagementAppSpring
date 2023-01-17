package com.example.project.service;

import com.example.project.constants.Constants;
import com.example.project.dto.StudentDto;
import com.example.project.entity.Student;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.exceptions.NotUniqueException;
import com.example.project.service.implementations.StudentServiceImpl;
import com.example.project.utils.StudentMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {
    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private com.example.project.repository.StudentRepository studentRepository;

    @Mock
    private com.example.project.mapper.StudentMapper studentMapper;

    @Test
    public void addStudentTest() {
        StudentDto studentDto = StudentMocks.mockStudentDto();
        Student student = StudentMocks.mockStudent();

        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.mapToStudent(studentDto)).thenReturn(student);
        when(studentMapper.mapToStudentDto(student)).thenReturn(studentDto);
        StudentDto result = studentService.add(studentDto);

        assertEquals(result.getName(), studentDto.getName());
        assertEquals(result.getEmail(), studentDto.getEmail());
    }

    @Test
    public void addStudentThrowsUniqueConstraintExceptionTest() {
        StudentDto studentDto = StudentMocks.mockStudentDto();
        Student student = StudentMocks.mockStudent();

        when(studentRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(student));
        NotUniqueException uniqueConstraintException = assertThrows(NotUniqueException.class, () -> studentService.add(studentDto));
        assertEquals(String.format(Constants.NOT_UNIQUE_ERROR, "Student", "email"), uniqueConstraintException.getMessage());
    }

    @Test
    public void searchStudentsWithNullParamTest() {
        StudentDto studentDto = StudentMocks.mockStudentDto();
        List<StudentDto> studentDtoList = Arrays.asList(studentDto);
        Student student = StudentMocks.mockStudent();
        List<Student> Students = Arrays.asList(student);

        when(studentRepository.findAll()).thenReturn(Students);
        when(studentMapper.mapToStudentDto(student)).thenReturn(studentDto);
        List<StudentDto> result = studentService.searchStudents(null);

        assertEquals(result, studentDtoList);
    }

    @Test
    public void searchStudentsWithParamTest() {
        StudentDto studentDto = StudentMocks.mockStudentDto();
        List<StudentDto> StudentDtoList = Arrays.asList(studentDto);
        Student student = StudentMocks.mockStudent();
        List<Student> students = Arrays.asList(student);

        when(studentMapper.mapToStudentDto(student)).thenReturn(studentDto);
        when(studentRepository.searchByName("test")).thenReturn(students);
        List<StudentDto> result = studentService.searchStudents("test");

        assertEquals(result, StudentDtoList);
    }

    @Test
    public void searchStudentsThrowsEntityNotFoundExceptionTest() {
        NoEntityFoundException entityNotFoundException = assertThrows(NoEntityFoundException.class, () -> studentService.searchStudents("test"));
        assertEquals(String.format(Constants.STUDENTS_NOT_FOUND, "test"), entityNotFoundException.getMessage());
    }
}