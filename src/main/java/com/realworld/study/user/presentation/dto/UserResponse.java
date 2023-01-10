package com.realworld.study.user.presentation.dto;

import com.realworld.study.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class UserResponse {

    private String email;
    private String username;
    private String bio;
    private String image;

    public UserResponse(final String email,
            final String username,
            final String bio,
            final String image) {
        this.email = email;
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    public static UserResponse of(final UserDetails userDetails) {
        return UserResponse.builder()
                .email(userDetails.getUsername())
                .build();
    }

    public static UserResponse of(final User user) {
        return UserResponse.builder()
                .username(user.getEmail())
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
