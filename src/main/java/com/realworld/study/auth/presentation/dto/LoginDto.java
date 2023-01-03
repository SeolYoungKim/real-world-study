package com.realworld.study.auth.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginDto {

    private String email;
    private String password;

    public LoginDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }
}
