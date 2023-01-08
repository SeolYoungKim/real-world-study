package com.realworld.study.post.domain;

import com.realworld.study.comment.domain.Comment;
import com.realworld.study.comment.domain.Comments;
import com.realworld.study.member.domain.Member;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @Embedded
    private PostTitle title;

    @Embedded
    private PostBody body;

    @Embedded
    private Comments comments;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected Post() {
    }

    public Post(
        Member member,
        PostTitle title,
        PostBody body,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this(null, member, title, body, createdAt, updatedAt);
    }

    public Post(
        Long id,
        Member member,
        PostTitle title,
        PostBody body,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title.getTitle();
    }

    public String getBody() {
        return body.getBody();
    }

    public List<Comment> getComments() {
        return comments.getComments();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(id, post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}