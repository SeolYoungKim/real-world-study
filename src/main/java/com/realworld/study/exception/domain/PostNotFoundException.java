package com.realworld.study.exception.domain;

import org.springframework.http.HttpStatus;

public class PostNotFoundException extends NotFoundException {
    private static final String ERROR_MESSAGE = "없는 게시글 입니다.";

    public PostNotFoundException() {
        super(ERROR_MESSAGE);
    }

    @Override
    public String getCode() {
        return HttpStatus.NOT_FOUND.name();
    }
}
