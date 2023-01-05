package com.realworld.study.auth.jwt.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TokenDto {
    private String token;

    public TokenDto(final String token) {
        this.token = token;
    }
}
