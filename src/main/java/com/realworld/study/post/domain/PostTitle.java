package com.realworld.study.post.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class PostTitle {

    private String title;

    protected PostTitle() {

    }

    public PostTitle(String title) {
        this.title = title;
    }
}
