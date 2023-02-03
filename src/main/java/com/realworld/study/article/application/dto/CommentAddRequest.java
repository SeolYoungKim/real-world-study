package com.realworld.study.article.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentAddRequest {

    private String body;

    public CommentAddRequest(final String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "CommentAddRequest{" +
                "body='" + body + '\'' +
                '}';
    }
}
