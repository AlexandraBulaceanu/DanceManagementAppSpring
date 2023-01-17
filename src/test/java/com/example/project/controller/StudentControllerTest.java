package com.example.project.controller;

import com.example.project.constants.Constants;
import com.example.project.controllers.StudentController;
import com.example.project.dto.StudentDto;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.exceptions.NotUniqueException;
import com.example.project.service.StudentService;
import com.example.project.service.implementations.StudentServiceImpl;
import com.example.project.utils.StudentMocks;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerTest {
    @MockBean
    private final StudentServiceImpl studentService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public StudentControllerTest(StudentServiceImpl studentService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.studentService = studentService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void addStudentTest() throws Exception {
        StudentDto studentDto = StudentMocks.mockStudentDto();
        when(studentService.add(any())).thenReturn(studentDto);

        String studentDtoBody = objectMapper.writeValueAsString(studentDto);
        MvcResult result = mockMvc.perform(post("/students")
                .content(studentDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(studentDto.getName()))
                .andExpect(jsonPath("$.email").value(studentDto.getEmail()))
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), studentDtoBody);
    }

    @Test
    public void addStudentThrowsUniqueConstraintExceptionTest() throws Exception {
        StudentDto studentDto = StudentMocks.mockStudentDto();
        when(studentService.add(any())).thenThrow(new NotUniqueException(String.format(Constants.NOT_UNIQUE_ERROR, "name")));

        String studentDtoBody = objectMapper.writeValueAsString(studentDto);
        mockMvc.perform(post("/students")
                .content(studentDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    public void searchStudentsTest() throws Exception {
        StudentDto studentDto = StudentMocks.mockStudentDto();
        when(studentService.searchStudents(any())).thenReturn(Arrays.asList(studentDto));

        String studentDtoBody = objectMapper.writeValueAsString(Arrays.asList(studentDto));
        MvcResult result = mockMvc.perform(get("/students").requestAttr("searchParam", "test"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), studentDtoBody);
    }

    @Test
    public void searchStudentsThrowsEntityNotFoundExceptionTest() throws Exception {
        when(studentService.searchStudents(any())).thenThrow(new NoEntityFoundException(String.format(Constants.STUDENTS_NOT_FOUND, "test")));
        mockMvc.perform(get("/students").requestAttr("searchParam", "test"))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}