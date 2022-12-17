package com.realworld.study.article.application.dto;

import com.realworld.study.article.domain.Article;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleRequest {

    private String title;
    private String description;
    private String contents;

    public Article toEntity() {
        return new Article(title, description, contents);
    }

    @Override
    public String toString() {
        return "ArticleRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
