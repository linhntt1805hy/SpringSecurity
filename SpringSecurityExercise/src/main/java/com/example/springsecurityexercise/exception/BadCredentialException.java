package com.example.springsecurityexercise.exception;

public class BadCredentialException extends RuntimeException{
    public BadCredentialException(String message) {
        super(message);
    }
}
