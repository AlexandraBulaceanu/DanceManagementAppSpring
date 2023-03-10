package com.example.project.mapper;

import com.example.project.dto.StudioDetailsDto;
import com.example.project.dto.StudioDto;
import com.example.project.entity.Studio;
import org.springframework.stereotype.Component;

@Component
public class StudioMapper {

    public StudioDto mapToStudioDto(Studio studio) {
        return StudioDto.builder()
                .name(studio.getName())
                .capacity(studio.getCapacity())
                .build();
    }

    public Studio mapToStudio(StudioDto studioDto) {
        return Studio.builder()
                .name(studioDto.getName())
                .capacity(studioDto.getCapacity())
                .build();
    }

    public StudioDetailsDto mapToStudioDetailsDto(Studio studio) {
        return StudioDetailsDto.builder()
                .id(studio.getStudio_id())
                .name(studio.getName())
                .capacity(studio.getCapacity())
                .build();
    }
}
