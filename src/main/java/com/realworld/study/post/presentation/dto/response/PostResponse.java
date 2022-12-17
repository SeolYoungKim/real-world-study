package com.realworld.study.post.presentation.dto.response;

import com.realworld.study.post.domain.Post;
import lombok.Getter;

@Getter
public class PostResponse {
    private final Long id;
    private final String title;
    private final String contents;

    public static PostResponse from(final Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContents());
    }

    private PostResponse(final Long id, final String title, final String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }
}
