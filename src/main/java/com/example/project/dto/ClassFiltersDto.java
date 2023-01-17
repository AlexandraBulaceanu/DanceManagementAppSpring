package com.example.project.dto;

import com.example.project.enums.EDayOfWeek;
import com.example.project.enums.ELevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassFiltersDto {
    private String name;

    private EDayOfWeek dayOfWeek;

    private ELevel level;
}