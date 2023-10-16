package com.study.jwt.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionResponseDto {

    private String statusCode;

    private String message;

    @Builder
    public ExceptionResponseDto(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
