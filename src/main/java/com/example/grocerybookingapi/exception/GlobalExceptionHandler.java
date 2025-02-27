package com.example.grocerybookingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.grocerybookingapi.util.ResponseWrapper;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<ResponseWrapper<String>> handleDuplicateEmailException(DuplicateFieldException ex) {
        ResponseWrapper<String> response = new ResponseWrapper<>(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<String>> handleException(Exception ex) {
        ResponseWrapper<String> response = new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
