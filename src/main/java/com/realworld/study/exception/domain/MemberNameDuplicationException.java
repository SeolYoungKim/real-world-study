package com.realworld.study.exception.domain;

public class MemberNameDuplicationException extends RuntimeException {
    private static final String ERROR_MESSAGE = "이미 존재하는 이름입니다.";

    public MemberNameDuplicationException() {
        super(ERROR_MESSAGE);
    }
}
