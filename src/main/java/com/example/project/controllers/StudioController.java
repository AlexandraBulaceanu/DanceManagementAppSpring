package com.example.project.controllers;

import com.example.project.dto.StudioDetailsDto;
import com.example.project.dto.StudioDto;
import com.example.project.service.StudioService;
import com.example.project.service.implementations.StudioServiceImpl;
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
@RequestMapping("/")
@Tag(name = "Studios management", description = "Manage studios from database")
public class StudioController {
    private final StudioServiceImpl studioService;

    @Autowired
    public StudioController(StudioServiceImpl studioService) {
        this.studioService = studioService;
    }

    @GetMapping
    @Operation(summary = "Get all studios", description = "Get information about all studios in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The studios list.")
    })
    public ResponseEntity<List<StudioDetailsDto>> getStudios() {
        return ResponseEntity.ok(studioService.getStudios());
    }

    @PostMapping
    @Operation(summary = "Add a studio", description = "Add a studio in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully add the studio"),
            @ApiResponse(responseCode = "409", description = "Duplicate name for studios is not allowed")
    })
    public ResponseEntity<StudioDto> addStudio(@Parameter(description = "cinema studio details") @Valid @RequestBody StudioDto studioDto) {
        return ResponseEntity.ok(studioService.add(studioDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit a studio", description = "Edit details of a studio from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully edit the studio"),
            @ApiResponse(responseCode = "404", description = "Studio not found"),
            @ApiResponse(responseCode = "409", description = "Duplicate name for studios is not allowed")
    })
    public ResponseEntity<StudioDto> editStudio(@Parameter(description = "the id of the studio that will be edited") @PathVariable Long id,
                                            @Parameter(description = "the new details of the studio") @Valid @RequestBody StudioDto studioDto) {
        return ResponseEntity.ok(studioService.editStudio(id, studioDto));
    }
}