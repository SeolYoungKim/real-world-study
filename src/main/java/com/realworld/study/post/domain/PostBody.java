package com.realworld.study.post.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;

@Embeddable
public class PostBody {

    @Lob
    private String body;

    protected PostBody() {

    }

    public PostBody(String body) {
        this.body = body;
    }
}
