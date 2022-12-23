package com.realworld.study.article.presentation.dto;

import com.realworld.study.article.domain.Article;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleCreateResponse {

    private Long id;
    private String title;
    private String description;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ArticleCreateResponse(final Long id,
            final String title,
            final String description,
            final String body,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ArticleCreateResponse of(final Article article) {
        return new ArticleCreateResponse(article.getId(),
                article.getTitle(),
                article.getDescription(),
                article.getBody(),
                article.getCreatedAt(),
                article.getUpdatedAt());
    }

    @Override
    public String toString() {
        return "ArticleCreateResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
