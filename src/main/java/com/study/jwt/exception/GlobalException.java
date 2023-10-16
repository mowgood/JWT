package com.study.jwt.exception;

import lombok.Getter;

@Getter
public abstract class GlobalException extends RuntimeException {

    public GlobalException(String message) {
        super(message);
    }

    public abstract int getStatusCode();
}
