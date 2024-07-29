package com.example.springsecurityexercise.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private long timestamp;
}
