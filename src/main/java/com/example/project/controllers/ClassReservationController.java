package com.example.project.controllers;

import com.example.project.dto.ClassReservationDetailsDto;
import com.example.project.dto.ClassReservationDto;
import com.example.project.service.implementations.ClassReservationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/class_reservations")
@Tag(name = "ClassReservations management", description = "Manage ClassReservations")
public class ClassReservationController {
    private final ClassReservationServiceImpl classReservationService;

    @Autowired
    public ClassReservationController(ClassReservationServiceImpl classReservationService) {
        this.classReservationService = classReservationService;
    }

    @PostMapping
    @Operation(summary = "Add an classReservation", description = "ClassReservation for a class")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully add the class reservation"),
            @ApiResponse(responseCode = "400", description = "The class is not scheduled at requested day of week and time"),
            @ApiResponse(responseCode = "400", description = "The class reservation is not allowed to be made in the past"),
            @ApiResponse(responseCode = "400", description = "Class fully booked"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "404", description = "Class not found"),
            @ApiResponse(responseCode = "404", description = "Studio not found")
    })
    public ResponseEntity<ClassReservationDetailsDto> addClassReservation(@Parameter(description = "details about class reservation") @Valid @RequestBody ClassReservationDto classReservationDto) {
        return ResponseEntity.ok(classReservationService.add(classReservationDto));
    }
}