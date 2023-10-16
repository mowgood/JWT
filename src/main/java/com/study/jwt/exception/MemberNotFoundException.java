package com.study.jwt.exception;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends GlobalException {

    private static final String MESSAGE = "유효하지 않은 회원입니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}