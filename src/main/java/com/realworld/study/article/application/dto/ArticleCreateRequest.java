package com.realworld.study.article.application.dto;

import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleCreateRequest {

    private String title;
    private String description;
    private String body;
    private User author;

    public ArticleCreateRequest(String title, String description, String body, User author) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.author = author;
    }

    public Article toEntity() {
        return new Article(title, description, body, author);
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
