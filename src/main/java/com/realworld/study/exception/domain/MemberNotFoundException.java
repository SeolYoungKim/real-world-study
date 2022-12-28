package com.realworld.study.exception.domain;

public class MemberNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "없는 회원입니다.";

    public MemberNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
