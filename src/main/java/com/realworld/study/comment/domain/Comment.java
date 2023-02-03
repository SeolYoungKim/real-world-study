package com.realworld.study.comment.domain;

import com.realworld.study.BaseTimeEntity;
import com.realworld.study.member.domain.Member;
import com.realworld.study.post.domain.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    public Comment(final String body, final Post post, final Member author) {
        validate(body);
        this.body = body;
        this.post = post;
        this.author = author;
    }

    private void validate(final String body) {
        if (!StringUtils.hasText(body)) {
            throw new IllegalArgumentException("댓글의 내용은 공백일 수 없습니다.");
        }
    }

    public String getAuthorName() {
        return author.getMemberName();
    }

    public boolean writtenBy(final Member member) {
        return author.equals(member);
    }
}
