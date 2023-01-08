package com.realworld.study.comment.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class CommentContent {

    private String content;

    protected CommentContent() {
    }

    public CommentContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }
}