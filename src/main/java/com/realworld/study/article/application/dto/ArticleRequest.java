package com.realworld.study.article.application.dto;

import com.realworld.study.article.domain.Article;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleRequest {

    private String title;
    private String description;
    private String body;

    public ArticleRequest(String title, String description, String body) {
        this.title = title;
        this.description = description;
        this.body = body;
    }

    public Article toEntity() {
        return new Article(title, description, body);
    }

    @Override
    public String toString() {
        return "ArticleRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
