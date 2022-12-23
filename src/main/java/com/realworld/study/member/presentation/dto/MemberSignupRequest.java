package com.realworld.study.member.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSignupRequest {
    private String email;
    private String password;
    private String memberName;

    public MemberSignupRequest(final String email, final String password, final String memberName) {
        this.email = email;
        this.password = password;
        this.memberName = memberName;
    }
}
