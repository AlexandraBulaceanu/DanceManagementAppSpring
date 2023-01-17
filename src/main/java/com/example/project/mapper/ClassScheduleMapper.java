package com.example.project.mapper;

import com.example.project.dto.ClassScheduleDto;
import com.example.project.entity.ClassSchedule;
import org.springframework.stereotype.Component;

@Component
public class ClassScheduleMapper {

    public ClassScheduleDto mapToClassScheduleDto(ClassSchedule classSchedule) {
        return ClassScheduleDto.builder()
                .className(classSchedule.getClass().getName())
                .studioName(classSchedule.getStudio().getName())
                .dayOfWeek(classSchedule.getId().getDayOfWeek())
                .startTime(classSchedule.getId().getStartTime())
                .price(classSchedule.getClassPrice())
                .build();
    }
}
