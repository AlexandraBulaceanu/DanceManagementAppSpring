package com.example.project.service;

import java.util.List;

public interface StudioService {
    List<StudioDetailsDto> getStudios();

    StudioDto editStudio(Long id, StudioDto studioDto);
}