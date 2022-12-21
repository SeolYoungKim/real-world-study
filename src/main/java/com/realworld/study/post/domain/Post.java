package com.realworld.study.post.domain;

import com.realworld.study.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@Entity  //TODO Entity 애노테이션에는 왜 아래와 같은 "기본 생성자"가 필요한가?
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //TODO CGLIB
public class Post extends BaseTimeEntity {
    @Id @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //TODO 이 애너테이션의 역할은 ?
    private Long id;

    @Column
    private String title;

    @Column
    private String contents;

    public Post(final String title, final String contents) {
        validate(title, contents);
        this.title = title;
        this.contents = contents;
    }

    private void validate(final String title, final String contents) {
        if (!StringUtils.hasText(title) || !StringUtils.hasText(contents)) {
            throw new IllegalArgumentException("게시글의 제목과 내용은 공백일 수 없습니다.");
        }
    }

    public void update(final String title, final String contents) {
        this.title = updateOrNot(this.title, title);
        this.contents = updateOrNot(this.contents, contents);
    }

    private <T> T updateOrNot(final T mine, final T other) {
        if (other == null) {
            return mine;
        }
        return other;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
