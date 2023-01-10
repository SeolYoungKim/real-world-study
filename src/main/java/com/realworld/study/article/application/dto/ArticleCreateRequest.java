package com.realworld.study.article.application.dto;

import com.realworld.study.user.domain.User;
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

    public ArticleCreateRequest(final String title,
            final String description,
            final String body,
            final User author) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.author = author;
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
