package com.realworld.study.common.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends CustomException {

    private final int code;

    public ForbiddenException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
    }
}
