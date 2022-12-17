package com.realworld.study.post.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class PostDescription {

    private String description;

    protected PostDescription() {

    }

    public PostDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
