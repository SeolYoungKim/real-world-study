package com.realworld.study.post.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.realworld.study.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class PostTest {
    private final Member fakeMember = new Member("email@domain.com", "12345678", "kim", "my name is...", "image");

    @DisplayName("Post를 생성할 때")
    @Nested
    class construct {
        private final String title = "제목";
        private final String contents = "내용";


        @DisplayName("제목과 내용이 모두 전달된 경우 성공적으로 생성된다.")
        @Test
        void constructSuccess() {
            new Post(title, contents, fakeMember);
        }

        @DisplayName("제목 또는 내용이 전달되지 않은 경우 예외를 발생시킨다.")
        @Nested
        class constructFail {
            private static final String EXCEPTION_MESSAGE = "게시글의 제목과 내용은 공백일 수 없습니다.";

            @ParameterizedTest(name = "제목이 전달되지 않은 경우. 입력: {0}")
            @NullAndEmptySource
            void notReceivedTitle(String nullOrEmpty) {
                assertThatThrownBy(() -> new Post(nullOrEmpty, contents, fakeMember))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(EXCEPTION_MESSAGE);
            }

            @ParameterizedTest(name = "내용이 전달되지 않은 경우. 입력: {0}")
            @NullAndEmptySource
            void notReceivedContents(String nullOrEmpty) {
                assertThatThrownBy(() -> new Post(title, nullOrEmpty, fakeMember))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(EXCEPTION_MESSAGE);
            }

            @ParameterizedTest(name = "내용이 전달되지 않은 경우. 입력: {0}")
            @NullAndEmptySource
            void notReceivedAnyParams(String nullOrEmpty) {
                assertThatThrownBy(() -> new Post(nullOrEmpty, nullOrEmpty, fakeMember))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(EXCEPTION_MESSAGE);
            }
        }
    }

    @DisplayName("Post를 update할 값이 전달되었을 때")
    @Nested
    class update {
        private Post post;

        @BeforeEach
        void setUp() {
            post = new Post("제목", "내용", fakeMember);
        }

        @DisplayName("제목과 내용을 변경할 값이 모두 전달된 경우 제목과 내용이 모두 변경된다.")
        @Test
        void receiveTitleAndContents() {
            String titleForUpdate = "title";
            String contentsForUpdate = "contents";
            post.update(titleForUpdate, contentsForUpdate);

            assertThat(post.getTitle()).isEqualTo(titleForUpdate);
            assertThat(post.getContents()).isEqualTo(contentsForUpdate);
        }

        @DisplayName("제목만 전달된 경우 제목만 변경된다.")
        @Test
        void receiveOnlyTitle() {
            String titleForUpdate = "title";
            String contentsBeforeUpdate = post.getContents();
            post.update(titleForUpdate, null);

            assertThat(post.getTitle()).isEqualTo(titleForUpdate);
            assertThat(post.getContents()).isEqualTo(contentsBeforeUpdate);
        }

        @DisplayName("내용만 전달된 경우 내용만 변경된다.")
        @Test
        void receiveOnlyContents() {
            String titleBeforeUpdate = post.getTitle();
            String contentsForUpdate = "contents";
            post.update(null, contentsForUpdate);

            assertThat(post.getTitle()).isEqualTo(titleBeforeUpdate);
            assertThat(post.getContents()).isEqualTo(contentsForUpdate);
        }
    }
}