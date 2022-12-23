package com.realworld.study.post.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUpdateRequest {
    private String title;
    private String contents;

    public PostUpdateRequest(final String title, final String contents) {
        this.title = title;
        this.contents = contents;
    }
}
