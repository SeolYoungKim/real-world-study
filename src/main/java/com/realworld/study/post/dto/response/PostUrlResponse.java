package com.realworld.study.post.dto.response;

public class PostUrlResponse {

    private Long id;

    private PostUrlResponse() {
    }

    public PostUrlResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
}