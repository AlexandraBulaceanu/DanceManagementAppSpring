package com.example.project.dto;

import com.example.project.enums.EDayOfWeek;
import com.example.project.enums.ELevel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDetailsDto {
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
    @Size(min = 1, message = "There must be at least one instructor for a class")
    private List<InstructorDto> instructors;

    @NotNull(message = "Schedule details must be defined")
    private List<ClassScheduleDetails> scheduleDetails;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClassScheduleDetails {
        private EDayOfWeek dayOfWeek;
        private LocalTime startTime;
        private Integer classPrice;
        private String studioName;
    }
}