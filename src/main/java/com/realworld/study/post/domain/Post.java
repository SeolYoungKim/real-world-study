package com.realworld.study.post.domain;

import com.querydsl.core.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String contents;

    public Post(String title, String contents) {
        validate(title, contents);
        this.title = title;
        this.contents = contents;
    }

    private void validate(String title, String contents) {
        if (StringUtils.isNullOrEmpty(title) || StringUtils.isNullOrEmpty(contents)) {
            throw new IllegalArgumentException("게시글의 제목과 내용은 공백일 수 없습니다.");
        }
    }

    public void update(String title, String contents) {
        this.title = updateOrNot(this.title, title);
        this.contents = updateOrNot(this.contents, contents);
    }

    private <T> T updateOrNot(T mine, T other) {
        if (other == null) {
            return mine;
        }
        return other;
    }
}
