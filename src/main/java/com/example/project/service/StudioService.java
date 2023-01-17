package com.example.project.service;

import com.example.project.dto.StudioDetailsDto;
import com.example.project.dto.StudioDto;

import java.util.List;

public interface StudioService {
    List<StudioDetailsDto> getStudios();

    StudioDto editStudio(Long id, StudioDto studioDto);
}