package com.realworld.study.common.exception;

public abstract class CustomException extends RuntimeException {

    public CustomException(final String message) {
        super(message);
    }

    public abstract int getCode();
}
