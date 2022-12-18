package com.realworld.study.post.presentation.dto.response;

import com.realworld.study.post.domain.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public final class PostResponse {
    private final Long id;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static PostResponse from(final Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContents(),
                post.getCreatedAt(), post.getModifiedAt());
    }

    private PostResponse(Long id, String title, String contents,
            LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
