package com.example.project.controllers;

import com.example.project.dto.InstructorDetailsDto;
import com.example.project.dto.InstructorDto;
import com.example.project.service.implementations.InstructorServiceImpl;
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
@RequestMapping("/instructors")
@Tag(name = "Instructors management", description = "Manage instructors from database")
public class InstructorController {
    private final InstructorServiceImpl instructorService;

    @Autowired
    public InstructorController(InstructorServiceImpl instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    @Operation(summary = "Get all instructors", description = "Get information about all instructors in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The instructors list.")
    })
    public ResponseEntity<List<InstructorDetailsDto>> getInstructors() {
        return ResponseEntity.ok(instructorService.getInstructors());
    }

    @PostMapping
    @Operation(summary = "Add an instructor", description = "Add an instructor in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully add the instructor"),
            @ApiResponse(responseCode = "409", description = "Conflict exception. There can't be 2 instructors with the same name")
    })
    public ResponseEntity<InstructorDto> addActor(@Parameter(description = "instructor details") @Valid @RequestBody InstructorDto instructorDto) {
        return ResponseEntity.ok(instructorService.add(instructorDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit an instructor", description = "Edit details of an instructor from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully edit the instructor"),
            @ApiResponse(responseCode = "404", description = "Not found exception. There is no instructor having the provided id"),
            @ApiResponse(responseCode = "409", description = "Conflict exception. There can't be 2 instructors with the same name")
    })
    public ResponseEntity<InstructorDto> editInstructor(@Parameter(description = "the id of the instructor that will be edited") @PathVariable Long id,
                                              @Parameter(description = "the new details of the instructor") @Valid @RequestBody InstructorDto instructorDto) {
        return ResponseEntity.ok(instructorService.editInstructor(id, instructorDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an instructor", description = "Delete an instructor from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully edit the instructor"),
            @ApiResponse(responseCode = "400", description = "Bad request exception. The instructor teaches classes, so deletion is not allowed"),
            @ApiResponse(responseCode = "404", description = "No instructor found with this id")
    })
    public ResponseEntity<InstructorDto> deleteInstructor(@Parameter(description = "the id of the instructor that will be deleted") @PathVariable Long id) {
        return ResponseEntity.ok(instructorService.deleteInstructor(id));
    }
}