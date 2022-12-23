package com.realworld.study.post.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class PostTitle {

    private String title;

    protected PostTitle() {
    }

    public PostTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}