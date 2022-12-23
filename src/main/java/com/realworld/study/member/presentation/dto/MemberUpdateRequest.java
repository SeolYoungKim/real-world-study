package com.realworld.study.member.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdateRequest {
    private String email;
    private String bio;
    private String image;

    public MemberUpdateRequest(String email, String bio, String image) {
        this.email = email;
        this.bio = bio;
        this.image = image;
    }
}
