package com.example.springsecurityexercise.exception;

public class ConflictDataException extends RuntimeException{
    public ConflictDataException(String message) {
        super(message);
    }
}
