package com.realworld.study.member.application.dto;

import com.realworld.study.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberAuthResponse {
    private final String email;
    private final String token;
    private final String name;
    private final String bio;
    private final String image;

    //TODO 이렇게 생성할 때 from이라는 의미가 맞나? 고민해보자
    public static MemberAuthResponse from(final Member member, final String token) {
        return MemberAuthResponse.builder()
                .email(member.getEmailValue())
                .token(token)
                .name(member.getMemberName())
                .bio(member.getBio())
                .image(member.getImage())
                .build();
    }

    @Builder
    private MemberAuthResponse(final String email, final String token, final String name,
            final String bio, final String image) {
        this.email = email;
        this.token = token;
        this.name = name;
        this.bio = bio;
        this.image = image;
    }

    @Override
    public String toString() {
        return "MemberAuthResponse{" +
                "email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
