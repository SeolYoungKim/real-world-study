package com.realworld.study.exception.domain;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "없는 회원입니다.";

    public MemberNotFoundException() {
        super(ERROR_MESSAGE);
    }

    @Override
    public String getCode() {
        return HttpStatus.NOT_FOUND.name();
    }
}
