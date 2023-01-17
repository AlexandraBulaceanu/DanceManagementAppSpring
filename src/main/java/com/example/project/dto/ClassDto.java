package com.example.project.dto;

import com.example.project.entity.Instructor;
import com.example.project.enums.ELevel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDto {
    @NotBlank(message = "Class name must be defined")
    private String name;

    @NotBlank(message = "Class description must be defined")
    private String description;

    @NotNull(message = "Class duration must be defined")
    @Min(value = 1, message = "Duration can't be negative")
    private Integer hoursDuration;

    @NotNull(message = "Class level must be provided!")
    private ELevel level;

    @NotNull(message = "Class instructors must be defined")
    private List<Instructor> instructors;
}