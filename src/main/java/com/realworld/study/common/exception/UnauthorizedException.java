package com.realworld.study.common.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends CustomException {

    private final int code;

     public UnauthorizedException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
    }
}
