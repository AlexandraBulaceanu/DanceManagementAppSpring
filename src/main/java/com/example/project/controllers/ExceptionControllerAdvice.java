package com.example.project.controllers;

import com.example.project.exceptions.BadRequestException;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.exceptions.NotUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler({NotUniqueException.class})
    public ResponseEntity<String> handleUniqueConstraintException(Exception exception) {
        System.out.println(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler({NoEntityFoundException.class})
    public ResponseEntity<String> handleNotFoundException(Exception exception) {
        System.out.println(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> handleBadRequestException(Exception exception) {
        System.out.println(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInvalidField(MethodArgumentNotValidException exception){
        System.out.println(exception.getMessage());
        String invalidFields = "Invalid fields: \n"
                + exception.getBindingResult().getFieldErrors().stream()
                .map(e -> "Field: " + e.getField() + ", error: " + e.getDefaultMessage() + ", value: " + e.getRejectedValue())
                .collect(Collectors.joining("\n"));
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(invalidFields);
    }

}