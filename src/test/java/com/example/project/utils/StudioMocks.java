package com.example.project.utils;

import com.example.project.dto.StudioDetailsDto;
import com.example.project.dto.StudioDto;
import com.example.project.entity.Studio;

import java.util.ArrayList;

public class StudioMocks {
    public static StudioDto mockStudioDto() {
        return StudioDto.builder()
                .name("Studio1")
                .capacity(40)
                .build();
    }

    public static StudioDetailsDto mockStudioDetailsDto() {
        return StudioDetailsDto.builder()
                .id(1L)
                .name("Studio1")
                .capacity(40)
                .build();
    }

    public static StudioDetailsDto mockStudioDetailsDto(Long id) {
        return StudioDetailsDto.builder()
                .id(id)
                .name("Studio1 " + id)
                .capacity(id.intValue() * 10)
                .build();
    }

    public static Studio mockStudio() {
        return Studio.builder()
                .studio_id(1L)
                .name("Studio1")
                .capacity(40)
                .classSchedules(new ArrayList<>())
                .build();
    }
}