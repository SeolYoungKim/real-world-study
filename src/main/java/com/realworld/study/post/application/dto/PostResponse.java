package com.realworld.study.post.application.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.realworld.study.post.domain.Post;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class PostResponse {
    public static PostResponse from(final Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .author(post.getAuthorName())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

    private final Long id;
    private final String title;
    private final String contents;
    private final String author;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    @QueryProjection
    public PostResponse(final Long id, final String title, final String contents, final String author,
            final LocalDateTime createdAt, final LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", author='" + author + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
