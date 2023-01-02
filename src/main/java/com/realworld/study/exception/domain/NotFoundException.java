package com.realworld.study.exception.domain;

public abstract class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    abstract public String getCode();
}
