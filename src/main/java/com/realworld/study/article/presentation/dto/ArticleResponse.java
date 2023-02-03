package com.realworld.study.article.presentation.dto;

import com.realworld.study.article.domain.Article;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Getter
public class ArticleResponse {

    private Long id;
    private String title;
    private String description;
    private String body;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private boolean favorited;
    private int favoritesCount;

    public ArticleResponse(final Long id,
            final String title,
            final String description,
            final String body,
            final LocalDateTime createAt,
            final LocalDateTime updateAt,
            final boolean favorited,
            final int favoritesCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.body = body;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.favorited = favorited;
        this.favoritesCount = favoritesCount;
    }

    public static ArticleResponse of(final Article article, final boolean favorited) {
        return ArticleResponse.builder()
                .id(article.getId())
                .description(article.getDescription())
                .body(article.getBody())
                .createAt(article.getCreatedAt())
                .updateAt(article.getUpdatedAt())
                .favorited(favorited)
                .favoritesCount(article.getFavorites().size())
                .build();
    }

    public static ArticleResponse of(final Article article) {
        return ArticleResponse.of(article, false);
    }

    @Override
    public String toString() {
        return "ArticleResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
