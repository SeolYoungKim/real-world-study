package com.realworld.study.user.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MemberTest {
    @DisplayName("회원 객체를 생성할 때")
    @Nested
    class construct {
        @DisplayName("올바른 값을 입력할 경우 예외가 발생하지 않는다.")
        @Test
        void constructSuccess() {
            new Member("email@domain.com", "12345678", "이름", "바이오", "이미지");
        }

        @DisplayName("비밀번호를 8자 미만으로 입력하면 예외를 발생시킨다.")
        @Test
        void constructFail1() {
            assertThatThrownBy(() -> new Member("email@domain.com", "1234567", "이름", "바이오", "이미지"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("비밀번호는 8자리 이상 입력해 주세요.");
        }

        @ParameterizedTest(name = "멤버 이름을 빈 값을 입력하거나 입력하지 않을 경우 예외를 발생시킨다. 입력 : {0}")
        @NullAndEmptySource
        void constructFail2(String nullOrEmpty) {
            assertThatThrownBy(() -> new Member("email@domain.com", "1234567", nullOrEmpty, "바이오", "이미지"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("비밀번호는 8자리 이상 입력해 주세요.");
        }
    }
}