package com.example.springsecurityexercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler
    public ResponseEntity<ErrorObject> handlerBadCredentialException(BadCredentialException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleResourceNotFoundException (ResourceNotFoundException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleMissingDataException(MissingDataException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleConflictException(ConflictDataException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.CONFLICT.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
    }
}
