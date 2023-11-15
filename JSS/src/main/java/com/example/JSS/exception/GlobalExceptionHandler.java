package com.example.JSS.exception;

import org.hibernate.cfg.beanvalidation.DuplicationStrategyImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmailException(DuplicateEmailException ex) {
        HttpStatus status = HttpStatus.CONFLICT; // Set the appropriate status code
        return ResponseEntity.status(status).body("Duplicate email: " + ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // Set the appropriate status code
        return ResponseEntity.status(status).body("Bad Request: " + ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        HttpStatus status = HttpStatus.CONFLICT; // Set the appropriate status code
        return ResponseEntity.status(status).body("User already exists: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleDefaultException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // Set the appropriate status code
        return ResponseEntity.status(status).body("Internal Server Error: " + ex.getMessage());
    }
}

