package com.realworld.study.post.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequest {
    private String title;
    private String contents;

    public PostCreateRequest(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
