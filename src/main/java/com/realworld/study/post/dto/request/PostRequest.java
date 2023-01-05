package com.realworld.study.post.dto.request;

public class PostRequest {

    private String title;
    private String body;

    private PostRequest() {
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }
}