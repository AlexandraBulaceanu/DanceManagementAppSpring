package com.example.project.controller;

import com.example.project.constants.Constants;
import com.example.project.controllers.ClassReservationController;
import com.example.project.dto.ClassReservationDetailsDto;
import com.example.project.dto.ClassReservationDto;
import com.example.project.exceptions.BadRequestException;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.service.implementations.ClassReservationServiceImpl;
import com.example.project.utils.ClassReservationMocks;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClassReservationController.class)
public class ClassReservationControllerTest {
    @MockBean
    private final ClassReservationServiceImpl classReservationService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public ClassReservationControllerTest(ClassReservationServiceImpl classReservationService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.classReservationService = classReservationService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void addClassReservationTest() throws Exception {
        ClassReservationDto classReservationDto = ClassReservationMocks.mockClassReservationDto();
        ClassReservationDetailsDto classReservationDetailsDto = ClassReservationMocks.mockClassReservationDetailsDto();
        when(classReservationService.add(any())).thenReturn(classReservationDetailsDto);

        String classReservationDtoBody = objectMapper.writeValueAsString(classReservationDto);
        String classReservationDetailsDtoBody = objectMapper.writeValueAsString(classReservationDetailsDto);
        MvcResult result = mockMvc.perform(post("/classReservations")
                .content(classReservationDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.className").value(classReservationDetailsDto.getClassName()))
                .andExpect(jsonPath("$.studioName").value(classReservationDetailsDto.getStudioName()))
                .andExpect(jsonPath("$.dayOfWeek").value(classReservationDetailsDto.getDayOfWeek().toString()))
                .andExpect(jsonPath("$.price").value(classReservationDetailsDto.getPrice()))
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), classReservationDetailsDtoBody);
    }

    @Test
    public void addClassReservationThrowsStudentEntityNotFoundExceptionTest() throws Exception {
        ClassReservationDto classReservationDto = ClassReservationMocks.mockClassReservationDto();
        when(classReservationService.add(any())).thenThrow(new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "student", "email", classReservationDto.getStudentEmail())));

        String classReservationDtoBody = objectMapper.writeValueAsString(classReservationDto);
        mockMvc.perform(post("/classReservations")
                .content(classReservationDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void addClassReservationThrowsClassEntityNotFoundExceptionTest() throws Exception {
        ClassReservationDto classReservationDto = ClassReservationMocks.mockClassReservationDto();
        when(classReservationService.add(any())).thenThrow(new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "class", "name", classReservationDto.getClassName())));

        String classReservationDtoBody = objectMapper.writeValueAsString(classReservationDto);
        mockMvc.perform(post("/classReservations")
                .content(classReservationDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void addClassReservationThrowsStudioEntityNotFoundExceptionTest() throws Exception {
        ClassReservationDto classReservationDto = ClassReservationMocks.mockClassReservationDto();
        when(classReservationService.add(any())).thenThrow(new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "studio", "name", classReservationDto.getStudioName())));

        String classReservationDtoBody = objectMapper.writeValueAsString(classReservationDto);
        mockMvc.perform(post("/classReservations")
                .content(classReservationDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void addClassReservationThrowsClassScheduleBadRequestExceptionTest() throws Exception {
        ClassReservationDto classReservationDto = ClassReservationMocks.mockClassReservationDto();
        when(classReservationService.add(any())).thenThrow(new BadRequestException(Constants.SCHEDULE_ERROR));

        String classReservationDtoBody = objectMapper.writeValueAsString(classReservationDto);
        mockMvc.perform(post("/classReservations")
                .content(classReservationDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void addClassReservationThrowsReservationBadRequestExceptionTest() throws Exception {
        ClassReservationDto classReservationDto = ClassReservationMocks.mockClassReservationDto();
        when(classReservationService.add(any())).thenThrow(new BadRequestException(Constants.RESERVATION_ERROR));

        String classReservationDtoBody = objectMapper.writeValueAsString(classReservationDto);
        mockMvc.perform(post("/classReservations")
                .content(classReservationDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void addClassReservationThrowsFullyBookedBadRequestExceptionTest() throws Exception {
        ClassReservationDto classReservationDto = ClassReservationMocks.mockClassReservationDto();
        when(classReservationService.add(any())).thenThrow(new BadRequestException(Constants.CLASS_FULLY_BOOKED));

        String classReservationDtoBody = objectMapper.writeValueAsString(classReservationDto);
        mockMvc.perform(post("/classReservations")
                .content(classReservationDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}