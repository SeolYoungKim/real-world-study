package com.realworld.study.post.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUpdateRequest {
    private String title;
    private String contents;

    public PostUpdateRequest(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
