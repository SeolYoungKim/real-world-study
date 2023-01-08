package com.realworld.study.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends CustomException {

    private final int code;

    public BadRequestException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
    }
}
