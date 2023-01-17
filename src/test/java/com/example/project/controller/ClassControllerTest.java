package com.example.project.controller;

import com.example.project.constants.Constants;
import com.example.project.controllers.ClassController;
import com.example.project.dto.ClassDetailsDto;
import com.example.project.dto.ClassFiltersDto;
import com.example.project.dto.ClassScheduleDto;
import com.example.project.dto.NewClassDto;
import com.example.project.exceptions.BadRequestException;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.exceptions.NotUniqueException;
import com.example.project.service.implementations.ClassServiceImpl;
import com.example.project.utils.ClassMocks;
import com.example.project.utils.ClassScheduleMocks;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClassController.class)
public class ClassControllerTest {
    @MockBean
    private final ClassServiceImpl classService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public ClassControllerTest(ClassServiceImpl classService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.classService = classService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void addClassTest() throws Exception {
        NewClassDto newClassDto = ClassMocks.mockNewClassDto();
        ClassDetailsDto classDetailsDto = ClassMocks.mockClassDetailsDto();
        when(classService.add(newClassDto)).thenReturn(classDetailsDto);

        String newClassDtoBody = objectMapper.writeValueAsString(newClassDto);
        String classDetailsDtoBody = objectMapper.writeValueAsString(classDetailsDto);
        MvcResult result = mockMvc.perform(post("/classes")
                .content(newClassDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(classDetailsDto.getName()))
                .andExpect(jsonPath("$.description").value(classDetailsDto.getDescription()))
                .andExpect(jsonPath("$.level").value(classDetailsDto.getLevel().toString()))
                .andExpect(jsonPath("$.hoursDuration").value(classDetailsDto.getHoursDuration()))
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), classDetailsDtoBody);
    }

    @Test
    public void addClassThrowsNotUniqueException() throws Exception {
        NewClassDto newClassDto = ClassMocks.mockNewClassDto();
        when(classService.add(newClassDto)).thenThrow(new NotUniqueException(String.format(Constants.NOT_UNIQUE_ERROR, "name")));

        String newClassDtoBody = objectMapper.writeValueAsString(newClassDto);
        mockMvc.perform(post("/classes")
                .content(newClassDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    public void addClassThrowsNoEntityFoundExceptionTest() throws Exception {
        NewClassDto newClassDto = ClassMocks.mockNewClassDto();
        when(classService.add(newClassDto)).thenThrow(new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "instructor", "id", 1)));

        String newClassDtoBody = objectMapper.writeValueAsString(newClassDto);
        mockMvc.perform(post("/classes")
                .content(newClassDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void searchClassesTest() throws Exception {
        ClassFiltersDto classFiltersDto = ClassMocks.mockClasssFiltersDto();
        ClassDetailsDto classDetailsDto = ClassMocks.mockClassDetailsDto();
        when(classService.searchClasses(classFiltersDto.getName(), classFiltersDto.getDayOfWeek(), classFiltersDto.getLevel()))
                .thenReturn(Arrays.asList(classDetailsDto));

        String classFiltersDtoBody = objectMapper.writeValueAsString(classFiltersDto);
        String classDetailsDtoBody = objectMapper.writeValueAsString(Arrays.asList(classDetailsDto));
        MvcResult result = mockMvc.perform(post("/classes/search")
                .content(classFiltersDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), classDetailsDtoBody);
    }

    @Test
    public void scheduleClassTest() throws Exception {
        ClassScheduleDto classScheduleDto = ClassScheduleMocks.mockClassScheduleDto();
        when(classService.scheduleClass(any())).thenReturn(classScheduleDto);

        String classScheduleDtoBody = objectMapper.writeValueAsString(classScheduleDto);
        MvcResult result = mockMvc.perform(post("/classes/schedule")
                .content(classScheduleDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.className").value(classScheduleDto.getClassName()))
                .andExpect(jsonPath("$.studioName").value(classScheduleDto.getStudioName()))
                .andExpect(jsonPath("$.dayOfWay").value(classScheduleDto.getDayOfWeek().toString()))
                .andExpect(jsonPath("$.startTime").value(classScheduleDto.getStartTime().toString()))
                .andExpect(jsonPath("$.price").value(classScheduleDto.getPrice()))
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), classScheduleDtoBody);
    }

    @Test
    public void scheduleClassThrowsMovieEntityNotFoundExceptionTest() throws Exception {
        ClassScheduleDto classScheduleDto = ClassScheduleMocks.mockClassScheduleDto();
        when(classService.scheduleClass(any())).thenThrow(new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "class", "name", "Test class")));

        String movieScheduleDtoBody = objectMapper.writeValueAsString(classScheduleDto);
        mockMvc.perform(post("/classes/schedule")
                .content(movieScheduleDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void scheduleClassThrowsStudioEntityNotFoundExceptionTest() throws Exception {
        ClassScheduleDto classScheduleDto = ClassScheduleMocks.mockClassScheduleDto();
        when(classService.scheduleClass(any())).thenThrow(new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "studio", "name", "Test studio")));

        String movieScheduleDtoBody = objectMapper.writeValueAsString(classScheduleDto);
        mockMvc.perform(post("/classes/schedule")
                .content(movieScheduleDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void scheduleClassThrowsClassBadRequestExceptionTest() throws Exception {
        ClassScheduleDto classScheduleDto = ClassScheduleMocks.mockClassScheduleDto();
        when(classService.scheduleClass(any())).thenThrow(new BadRequestException(Constants.SCHEDULE_ERROR));

        String movieScheduleDtoBody = objectMapper.writeValueAsString(classScheduleDto);
        mockMvc.perform(post("/classes/schedule")
                .content(movieScheduleDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void scheduleClassThrowsStudioBadRequestExceptionTest() throws Exception {
        ClassScheduleDto classScheduleDto = ClassScheduleMocks.mockClassScheduleDto();
        when(classService.scheduleClass(any())).thenThrow(new BadRequestException(Constants.AVAILABILITY_ERROR));

        String movieScheduleDtoBody = objectMapper.writeValueAsString(classScheduleDto);
        mockMvc.perform(post("/classes/schedule")
                .content(movieScheduleDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}