package com.realworld.study.auth.jwt;

public class JwtNotAvailable extends RuntimeException {
    public JwtNotAvailable(String message) {
        super(message);
    }
}
