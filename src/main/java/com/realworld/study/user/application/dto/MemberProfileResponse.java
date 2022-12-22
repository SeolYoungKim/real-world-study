package com.realworld.study.user.application.dto;

import com.realworld.study.user.domain.Member;
import lombok.Getter;

@Getter
public class MemberProfileResponse {
    private final String name;
    private final String bio;
    private final String image;

    public static MemberProfileResponse from(final Member member) {
        return new MemberProfileResponse(
                member.getMemberName(),
                member.getBio(),
                member.getImage());
    }

    private MemberProfileResponse(final String name, final String bio, final String image) {
        this.name = name;
        this.bio = bio;
        this.image = image;
    }
}
