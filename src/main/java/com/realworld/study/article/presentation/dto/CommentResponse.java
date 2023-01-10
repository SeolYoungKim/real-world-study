package com.realworld.study.article.presentation.dto;

import com.realworld.study.article.domain.Comment;
import com.realworld.study.user.presentation.dto.UserResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Getter
public class CommentResponse {

    private Long id;
    private String body;
    private UserResponse author;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public CommentResponse(final Long id,
            final String body,
            final UserResponse author,
            final LocalDateTime createAt,
            final LocalDateTime updateAt) {
        this.id = id;
        this.body = body;
        this.author = author;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static CommentResponse of(final Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .author(UserResponse.of(comment.getAuthor()))
                .createAt(comment.getCreatedAt())
                .updateAt(comment.getCreatedAt())
                .build();
    }

    @Override
    public String toString() {
        return "CommentResponse{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", author=" + author +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
