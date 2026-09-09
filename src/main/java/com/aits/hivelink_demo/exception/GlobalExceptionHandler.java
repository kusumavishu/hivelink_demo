package com.aits.hivelink_demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    // 1. @Valid / @RequestBody VALIDATION
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity handleValidationException(
//            MethodArgumentNotValidException ex
//    ){
//
//    }
}
