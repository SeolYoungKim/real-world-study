package com.realworld.study.auth.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginRequestDto {

    private String email;
    private String password;

    public LoginRequestDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }
}
