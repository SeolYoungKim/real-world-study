package com.realworld.study.post.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateRequest {
    private String title;
    private String contents;

    public PostCreateRequest(final String title, final String contents) {
        this.title = title;
        this.contents = contents;
    }
}
