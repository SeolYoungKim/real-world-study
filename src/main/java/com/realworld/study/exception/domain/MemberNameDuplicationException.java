package com.realworld.study.exception.domain;

import org.springframework.http.HttpStatus;

public class MemberNameDuplicationException extends RealWorldException {
    private static final String ERROR_MESSAGE = "이미 존재하는 이름입니다.";

    public MemberNameDuplicationException() {
        super(ERROR_MESSAGE);
    }

    @Override
    public String getCode() {
        return HttpStatus.BAD_REQUEST.name();
    }
}
