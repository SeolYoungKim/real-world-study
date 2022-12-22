package com.realworld.study.post.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;

@Embeddable
@Access(AccessType.FIELD)
public class PostBody {

    @Lob
    private String body;

    protected PostBody() {
    }

    public PostBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}