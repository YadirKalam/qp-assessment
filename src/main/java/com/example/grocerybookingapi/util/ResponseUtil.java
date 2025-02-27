package com.example.grocerybookingapi.util;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    private ResponseUtil() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    public static <T> ResponseEntity<ResponseWrapper<T>> buildSuccessResponse(T data, String message) {
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), message, data));
    }

  
    public static <T> ResponseEntity<ResponseWrapper<T>> buildFailureResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ResponseWrapper<>(status.value(), message, null));
    }
}