package com.realworld.study.auth.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignUpRequestDto {

    private String username;
    private String email;
    private String password;

    public SignUpRequestDto(final String username,
            final String email,
            final String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
