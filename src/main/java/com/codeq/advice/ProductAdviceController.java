package com.codeq.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codeq.dto.ApiResponse;
import com.codeq.exception.TerminalProcessException;
import com.codeq.exception.TransientDBAccessException;

@RestControllerAdvice
public class ProductAdviceController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public ResponseEntity<ApiResponse<?>> handleError(
            Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ex.getLocalizedMessage(), ex.getCause()));
    }

    @ExceptionHandler(TerminalProcessException.class)
    public ResponseEntity<ApiResponse<?>> handleTerminalProcessException(
            TerminalProcessException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ex.getLocalizedMessage(), ex.getCause()));
    }

    @ExceptionHandler(TransientDBAccessException.class)
    public ResponseEntity<ApiResponse<?>> handleTransientDBAccessException(
            TransientDBAccessException ex) {

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiResponse.error(ex.getLocalizedMessage(), ex.getCause()));
    }
}
