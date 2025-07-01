package com.onlinebookstore.book_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> handleBookNotFoundEx(BookNotFoundException ex){
        Map<String, Object> errorMsg = new HashMap<>();
        errorMsg.put("timestamp", LocalDateTime.now());
        errorMsg.put("status", HttpStatus.NOT_FOUND.value());
        errorMsg.put("error", "Not found");
        errorMsg.put("message", ex.getMessage());

        return new ResponseEntity<>(errorMsg, HttpStatus.NOT_FOUND);
    }

}
