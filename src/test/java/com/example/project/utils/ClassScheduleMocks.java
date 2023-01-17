package com.example.project.utils;

import com.example.project.dto.ClassScheduleDto;
import com.example.project.entity.ClassSchedule;
import com.example.project.entity.Studio;
import com.example.project.entity.Class;
import com.example.project.enums.EDayOfWeek;
import com.example.project.helper.ClassScheduleId;
import java.time.LocalTime;
import java.util.ArrayList;

public class ClassScheduleMocks {
    public static ClassScheduleDto mockClassScheduleDto() {
        return ClassScheduleDto.builder()
                .className("Heels")
                .studioName("Studio1")
                .dayOfWeek(EDayOfWeek.TUESDAY)
                .startTime(LocalTime.of(21, 0, 0))
                .price(100)
                .build();
    }

    public static ClassSchedule mockClassSchedule(Class cls, Studio studio) {
        return ClassSchedule.builder()
                .id(mockClassScheduleId())
                .cls(cls)
                .studio(studio)
                .classPrice(100)
                .reservationTickets(new ArrayList<>())
                .build();
    }

    public static ClassScheduleId mockClassScheduleId() {
        return ClassScheduleId.builder()
                .classId(1L)
                .studioId(1L)
                .dayOfWeek(EDayOfWeek.TUESDAY)
                .startTime(LocalTime.of(21, 0, 0))
                .build();
    }
}
