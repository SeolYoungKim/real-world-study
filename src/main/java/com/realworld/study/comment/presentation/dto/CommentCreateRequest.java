package com.realworld.study.comment.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCreateRequest {
    private String body;

    public CommentCreateRequest(String body) {
        this.body = body;
    }
}
