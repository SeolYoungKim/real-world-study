package com.realworld.study.exception.domain;

public class PostNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "없는 게시글 입니다.";

    public PostNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
