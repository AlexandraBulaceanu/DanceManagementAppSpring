package com.example.project.exceptions;

public class NotUniqueException extends RuntimeException{
    public NotUniqueException(String message) {
        super(message);
    }
}
