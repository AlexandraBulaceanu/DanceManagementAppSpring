package com.example.project.helper;

import com.example.project.enums.EDayOfWeek;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ClassScheduleId implements Serializable {

    private Long classId;

    private Long studioId;

    private LocalTime startTime;

    private EDayOfWeek dayOfWeek;
}