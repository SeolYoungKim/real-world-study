package com.realworld.study.comment.domain;

import com.realworld.study.member.domain.Member;
import com.realworld.study.post.domain.Post;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CommentContent content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    protected Comment() {
    }

    public Comment(Long id, String content, Member member, Post post) {
        this.id = id;
        this.content = new CommentContent(content);
        this.member = member;
        this.post = post;
    }

    public Comment(String content, Member member, Post post) {
        this(null, content, member, post);
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}