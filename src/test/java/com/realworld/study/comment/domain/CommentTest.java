package com.realworld.study.comment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.realworld.study.member.domain.Member;
import com.realworld.study.post.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CommentTest {
    private Post post;
    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .memberName("kim")
                .email("email@email.com")
                .password("12345678")
                .build();

        post = new Post("title", "contents", member);
    }

    @DisplayName("Comment를 생성할 때")
    @Nested
    class construct {
        @DisplayName("body가 null 혹은 빈 값이 아닐 경우 Comment가 정상적으로 생성된다.")
        @Test
        void success() {
            new Comment("body", post, member);
        }

        @ParameterizedTest(name = "body가 null 혹은 빈 값일 경우 예외를 발생시킨다. 입력 : {0}")
        @NullAndEmptySource
        void fail(String nullOrEmptyBody) {
            assertThatThrownBy(() -> new Comment(nullOrEmptyBody, post, member))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("댓글의 내용은 공백일 수 없습니다.");
        }
    }

    @DisplayName("입력받은 member가 작성자인지 확인할 때")
    @Nested
    class writtenBy {
        @DisplayName("작성자일 경우 true를 반환한다.")
        @Test
        void isAuthor() {
            Comment comment = new Comment("body", post, member);
            assertThat(comment.writtenBy(member)).isTrue();
        }

        @DisplayName("작성자일 경우 false를 반환한다.")
        @Test
        void isNotAuthor() {
            Comment comment = new Comment("body", post, member);
            Member notAuthor = new Member("notAuthor@domain.com", "12345677", "name", "", "");
            assertThat(comment.writtenBy(notAuthor)).isFalse();
        }
    }
}