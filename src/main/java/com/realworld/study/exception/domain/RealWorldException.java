package com.realworld.study.exception.domain;

public abstract class RealWorldException extends RuntimeException {
    public RealWorldException(String message) {
        super(message);
    }

    abstract public String getCode();
}
