package com.example.project.controllers;

import com.example.project.dto.StudentDto;
import com.example.project.service.StudentService;
import com.example.project.service.implementations.StudentServiceImpl;
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
@RequestMapping("/students")
@Tag(name = "Students management", description = "Manage students from database")
public class StudentController {
    private final StudentServiceImpl studentService;

    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @Operation(summary = "Add student", description = "Add a student in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully add the student"),
            @ApiResponse(responseCode = "409", description = "Conflict exception. There can't be 2 students with the same email")
    })
    public ResponseEntity<StudentDto> addStudent(@Parameter(description = "Student details") @Valid @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.add(studentDto));
    }

    @GetMapping
    @Operation(summary = "Search students", description = "Search for students in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found students that match the provided criteria"),
            @ApiResponse(responseCode = "404", description = "No students whose name contains the value specified in search parameter")
    })
    public ResponseEntity<List<StudentDto>> searchStudents(@Parameter(description = "search param for name") @RequestParam(required = false) String searchParam) {
        return ResponseEntity.ok(studentService.searchStudents(searchParam));
    }
}