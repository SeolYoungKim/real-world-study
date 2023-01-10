package com.realworld.study.exception.domain;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "없는 회원입니다.";

    public CommentNotFoundException() {
        super(ERROR_MESSAGE);
    }

    @Override
    public String getCode() {
        return HttpStatus.NOT_FOUND.name();
    }
}
