package com.realworld.study.article.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleUpdateRequest {

    private String title;
    private String description;
    private String body;

    public ArticleUpdateRequest(String title, String description, String body) {
        this.title = title;
        this.description = description;
        this.body = body;
    }

    @Override
    public String toString() {
        return "ArticleUpdateRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
