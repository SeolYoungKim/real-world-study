package com.realworld.study.post.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class PostDescription {

    private String description;

    protected PostDescription() {

    }

    public PostDescription(String description) {
        this.description = description;
    }
}
