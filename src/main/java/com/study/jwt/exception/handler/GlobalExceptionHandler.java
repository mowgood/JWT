package com.study.jwt.exception.handler;

import com.study.jwt.exception.ExceptionResponseDto;
import com.study.jwt.exception.GlobalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionResponseDto> globalException(GlobalException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(ExceptionResponseDto.builder()
                .statusCode(String.valueOf(exception.getStatusCode()))
                .message(exception.getMessage())
                .build());
    }
}
