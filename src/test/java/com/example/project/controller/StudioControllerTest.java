package com.example.project.controller;

import com.example.project.constants.Constants;
import com.example.project.controllers.StudioController;
import com.example.project.dto.StudioDetailsDto;
import com.example.project.dto.StudioDto;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.exceptions.NotUniqueException;
import com.example.project.service.StudioService;
import com.example.project.service.implementations.StudioServiceImpl;
import com.example.project.utils.StudioMocks;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudioController.class)
public class StudioControllerTest {
    @MockBean
    private final StudioServiceImpl studioService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public StudioControllerTest(StudioServiceImpl studioService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.studioService = studioService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void getStudiosTest() throws Exception {
        List<StudioDetailsDto> studios = new ArrayList<>();
        for(long i = 1; i <= 5; i++) {
            studios.add(StudioMocks.mockStudioDetailsDto(i));
        }
        when(studioService.getStudios()).thenReturn(studios);

        String studiosBody = objectMapper.writeValueAsString(studios);
        MvcResult result = mockMvc.perform(get("/studios"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), studiosBody);
    }

    @Test
    public void addStudioTest() throws Exception {
        StudioDto StudioDto = StudioMocks.mockStudioDto();
        when(studioService.add(any())).thenReturn(StudioDto);

        String studioDtoBody = objectMapper.writeValueAsString(StudioDto);
        MvcResult result = mockMvc.perform(post("/studios")
                .content(studioDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(StudioDto.getName()))
                .andExpect(jsonPath("$.numberOfRows").value(12))
                .andExpect(jsonPath("$.seatsPerRow").value(10))
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), studioDtoBody);
    }

    @Test
    public void addStudioThrowsConflictExceptionTest() throws Exception {
        StudioDto StudioDto = StudioMocks.mockStudioDto();
        when(studioService.add(any())).thenThrow(new NotUniqueException(String.format(Constants.NOT_UNIQUE_ERROR, "name")));

        String studioDtoBody = objectMapper.writeValueAsString(StudioDto);
        mockMvc.perform(post("/studios")
                .content(studioDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    public void editStudioTest() throws Exception {
        StudioDto studioDto = StudioMocks.mockStudioDto();
        when(studioService.editStudio(any(), any())).thenReturn(studioDto);

        String studioDtoBody = objectMapper.writeValueAsString(studioDto);
        MvcResult result = mockMvc.perform(put("/studios/1")
                .content(studioDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(studioDto.getName()))
                .andExpect(jsonPath("$.numberOfRows").value(12))
                .andExpect(jsonPath("$.seatsPerRow").value(10))
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), studioDtoBody);
    }

    @Test
    public void editStudioThrowsNotFoundExceptionTest() throws Exception {
        StudioDto studioDto = StudioMocks.mockStudioDto();
        when(studioService.editStudio(any(), any())).thenThrow(new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "Studio", "id", 1)));

        String studioDtoBody = objectMapper.writeValueAsString(studioDto);
        mockMvc.perform(put("/studios/1")
                .content(studioDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void editStudioThrowsConflictExceptionTest() throws Exception {
        StudioDto studioDto = StudioMocks.mockStudioDto();
        when(studioService.editStudio(any(), any())).thenThrow(new NotUniqueException(String.format(Constants.NOT_UNIQUE_ERROR, "Studio", "name")));

        String studioDtoBody = objectMapper.writeValueAsString(studioDto);
        mockMvc.perform(put("/studios/1")
                .content(studioDtoBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
    }
}