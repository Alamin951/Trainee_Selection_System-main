package com.example.JSS.exception;

import org.hibernate.cfg.beanvalidation.DuplicationStrategyImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({DuplicateEmailException.class, IllegalArgumentException.class, UserAlreadyExistException.class})
    public ResponseEntity<String> handleDuplicateEmailException(Exception ex) {
        HttpStatus status = HttpStatus.CONFLICT; // Set the appropriate status code
        return ResponseEntity.status(status).body(ex.getMessage());
    }
}
