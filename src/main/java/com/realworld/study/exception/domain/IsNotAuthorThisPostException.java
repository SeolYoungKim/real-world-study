package com.realworld.study.exception.domain;

public class IsNotAuthorThisPostException extends RuntimeException {
    private static final String ERROR_MESSAGE = "해당 게시글의 저자가 아닙니다.";

    public IsNotAuthorThisPostException() {
        super(ERROR_MESSAGE);
    }
}
