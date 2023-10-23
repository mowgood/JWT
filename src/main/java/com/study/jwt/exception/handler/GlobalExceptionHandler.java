package com.study.jwt.exception.handler;

import com.study.jwt.exception.ExceptionResponseDto;
import com.study.jwt.exception.GlobalException;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> basicException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionResponseDto.builder()
                .statusCode(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()))
                .message("예기치 못한 오류가 발생했습니다. 다시 시도해 주세요.")
                .build());
    }
}
