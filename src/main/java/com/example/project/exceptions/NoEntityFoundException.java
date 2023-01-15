package com.example.project.exceptions;

public class NoEntityFoundException extends RuntimeException{
    public NoEntityFoundException(String message) {
        super(message);
    }
}
