package com.realworld.study.exception.domain;

import org.springframework.http.HttpStatus;

public class IsNotAuthorException extends RealWorldException {
    private static final String ERROR_MESSAGE = "해당 게시글 혹은 댓글의 저자가 아닙니다.";

    public IsNotAuthorException() {
        super(ERROR_MESSAGE);
    }

    @Override
    public String getCode() {
        return HttpStatus.BAD_REQUEST.name();
    }
}
