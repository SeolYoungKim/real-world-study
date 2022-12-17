package com.realworld.study.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * TODO 정체성을 좀 더 명확히 해보자.
 *  우리가 real world를 기반으로 구현을 하기로 하긴 했는데... 앱의 정체성이 모호한 기분이다.
 *  - Article의 역할이 무엇인가? 무슨 글을 담으려고 만든 것인가? 단순히 블로그인가? 트윗같은 SNS 단문 글인가? 신문 기사인가?
 *      - 더 나아가, User를 구현한다면, 내가 팔로우 하는것이 기자인가? 트위터같은 SNS의 유저인가?
 *  - description은 왜 필요할까? 어떤 기능을 하려고 만든 필드일까? -> 이런 생각을 안해본 것 같다. 그저 따라서 구현하는 것은 설계도를 그대로, 그니까 아무런 생각 없이 구현하는 것과 같아지는 것 같다.
 *  - 아무리 real world라는 소스를 기반으로 구현하고 있지만, 최종 앱의 형태는 무엇인가? 에 대한 고민을 해볼만 한 것 같다. 구현할수록 정체성이 헷갈렸다. (내 기준)
 *  - 제한 사항에 대한 논의가 부족했다. (title 글자 수 제한 등)
 */
@Getter
@Entity  //TODO Entity 애노테이션에는 왜 아래와 같은 "기본 생성자"가 필요한가?
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
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
}
