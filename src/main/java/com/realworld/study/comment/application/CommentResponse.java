package com.realworld.study.comment.application;

import com.realworld.study.comment.domain.Comment;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class CommentResponse {
    public static CommentResponse from(final Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .author(comment.getAuthorName())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

    private final Long id;
    private final String body;
    private final String author;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    private CommentResponse(final Long id, final String body, final String author,
            final LocalDateTime createdAt, final LocalDateTime modifiedAt) {
        this.id = id;
        this.body = body;
        this.author = author;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return "CommentResponse{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", author='" + author + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
