package com.example.project.controller;

import com.example.project.constants.Constants;
import com.example.project.controllers.InstructorController;
import com.example.project.dto.InstructorDetailsDto;
import com.example.project.dto.InstructorDto;
import com.example.project.exceptions.BadRequestException;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.exceptions.NotUniqueException;
import com.example.project.service.InstructorService;
import com.example.project.service.implementations.InstructorServiceImpl;
import com.example.project.utils.InstructorMocks;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InstructorController.class)
public class InstructorControllerTest {
    @MockBean
    private final InstructorServiceImpl instructorService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public InstructorControllerTest(InstructorServiceImpl instructorService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.instructorService = instructorService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void getInstructorsTest() throws Exception {
        List<InstructorDetailsDto> instructors = new ArrayList<>();
        for(long i = 1; i <= 5; i++) {
            instructors.add(InstructorMocks.mockInstructorDetailsDto(i));
        }
        when(instructorService.getInstructors()).thenReturn(instructors);

        String instructorsBody = objectMapper.writeValueAsString(instructors);
        MvcResult result = mockMvc.perform(get("/instructors"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), instructorsBody);
    }

    @Test
    public void addInstructorTest() throws Exception {
        InstructorDto instructorDto = InstructorMocks.mockInstructorDto();
        when(instructorService.add(any())).thenReturn(instructorDto);

        String instructorDtoBody = objectMapper.writeValueAsString(instructorDto);
        MvcResult result = mockMvc.perform(post("/instructors")
                .content(instructorDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(instructorDto.getName()))
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), instructorDtoBody);
    }

    @Test
    public void addInstructorThrowsConflictExceptionTest() throws Exception {
        InstructorDto instructorDto = InstructorMocks.mockInstructorDto();
        when(instructorService.add(any())).thenThrow(new NotUniqueException(String.format(Constants.NOT_UNIQUE_ERROR, "name")));

        String instructorDtoBody = objectMapper.writeValueAsString(instructorDto);
        mockMvc.perform(post("/instructors")
                .content(instructorDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    public void editInstructorTest() throws Exception {
        InstructorDto instructorDto = InstructorMocks.mockInstructorDto();
        when(instructorService.editInstructor(any(), any())).thenReturn(instructorDto);

        String instructorDtoBody = objectMapper.writeValueAsString(instructorDto);
        MvcResult result = mockMvc.perform(put("/instructors/1")
                .content(instructorDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(instructorDto.getName()))
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), instructorDtoBody);
    }

    @Test
    public void editInstructorThrowsNotFoundExceptionTest() throws Exception {
        InstructorDto instructorDto = InstructorMocks.mockInstructorDto();
        when(instructorService.editInstructor(any(), any())).thenThrow(new NoEntityFoundException(String.format(Constants.STUDENTS_NOT_FOUND, "Instructor", "id", 1)));

        String instructorDtoBody = objectMapper.writeValueAsString(instructorDto);
        mockMvc.perform(put("/instructors/1")
                .content(instructorDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void editInstructorThrowsConflictExceptionTest() throws Exception {
        InstructorDto instructorDto = InstructorMocks.mockInstructorDto();
        when(instructorService.editInstructor(any(), any())).thenThrow(new NotUniqueException(String.format(Constants.NOT_UNIQUE_ERROR, "name")));

        String instructorDtoBody = objectMapper.writeValueAsString(instructorDto);
        mockMvc.perform(put("/instructors/1")
                .content(instructorDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    public void deleteInstructorTest() throws Exception {
        InstructorDto instructorDto = InstructorMocks.mockInstructorDto();
        when(instructorService.deleteInstructor(any())).thenReturn(instructorDto);

        String instructorDtoBody = objectMapper.writeValueAsString(instructorDto);
        MvcResult result = mockMvc.perform(delete("/instructors/1"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), instructorDtoBody);
    }

    @Test
    public void deleteInstructorThrowsNotFoundExceptionTest() throws Exception {
        when(instructorService.deleteInstructor(any())).thenThrow(new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "Instructor", "id", 1)));

        mockMvc.perform(delete("/instructors/1"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deleteInstructorThrowsBasRequestExceptionTest() throws Exception {
        when(instructorService.deleteInstructor(any())).thenThrow(new BadRequestException(Constants.INSTRUCTOR_DELETION_ERROR));

        mockMvc.perform(delete("/instructors/1"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}