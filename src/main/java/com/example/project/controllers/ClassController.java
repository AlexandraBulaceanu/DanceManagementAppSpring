package com.example.project.controllers;

import com.example.project.dto.ClassDetailsDto;
import com.example.project.dto.ClassFiltersDto;
import com.example.project.dto.ClassScheduleDto;
import com.example.project.dto.NewClassDto;
import com.example.project.service.ClassService;
import com.example.project.service.implementations.ClassServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/classes")
@Tag(name = "Class management", description = "Manage Class from database")
public class ClassController {
    private final ClassServiceImpl classService;

    @Autowired
    public ClassController(ClassServiceImpl classService) {
        this.classService = classService;
    }

    @PostMapping
    @Operation(summary = "Add Class", description = "Add a class in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully add the class"),
            @ApiResponse(responseCode = "404", description = "Instructors not found for this class"),
            @ApiResponse(responseCode = "409", description = "Two classes cannot have the same name")
    })
    public ResponseEntity<ClassDetailsDto> addClass(@Parameter(description = "Class details") @Valid @RequestBody NewClassDto classDto) {
        return ResponseEntity.ok(classService.add(classDto));
    }

    @PostMapping("/search")
    @Operation(summary = "Search class", description = "Search for class in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found class that match the provided criteria"),
    })
    public ResponseEntity<List<ClassDetailsDto>> searchClass(@Parameter(description = "filters for class") @RequestBody ClassFiltersDto classFiltersDto) {
        return ResponseEntity.ok(classService.searchClasses(classFiltersDto.getName(), classFiltersDto.getDayOfWeek(), classFiltersDto.getLevel()));
    }

    @PostMapping("/schedule")
    @Operation(summary = "Schedule a class", description = "Schedule a class according to provided parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully schedule the class"),
            @ApiResponse(responseCode = "400", description = "The class can't be scheduled in a past date"),
            @ApiResponse(responseCode = "400", description = "The studio is not available at the requested time"),
            @ApiResponse(responseCode = "404", description = "The class doesn't exist"),
            @ApiResponse(responseCode = "404", description = "The studio doesn't exist")
    })
    public ResponseEntity<ClassScheduleDto> scheduleClass(@Parameter(description = "details of the class, the studio and schedule") @Valid @RequestBody ClassScheduleDto ClassScheduleDto) {
        return ResponseEntity.ok(classService.scheduleClass(ClassScheduleDto));
    }
}