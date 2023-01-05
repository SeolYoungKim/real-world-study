package com.realworld.study.auth;

public enum Role {
    USER("ROLE_USER");

    private final String key;

    Role(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
