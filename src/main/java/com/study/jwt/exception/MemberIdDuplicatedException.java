package com.study.jwt.exception;

import org.springframework.http.HttpStatus;

public class MemberIdDuplicatedException extends GlobalException{

    private static final String MESSAGE = "중복된 이메일 입니다.";

    public MemberIdDuplicatedException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
