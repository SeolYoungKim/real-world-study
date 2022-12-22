package com.realworld.study.user.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {
    @DisplayName("이메일 객체를 생성할 때")
    @Nested
    class construct {
        @DisplayName("올바른 이메일 주소를 입력하면 예외가 발생하지 않는다.")
        @Test
        void constructSuccess() {
            final String correctEmail = "user@domain.com";
            Email email = new Email(correctEmail);
        }

        @ParameterizedTest(name = "올바르지 않은 이메일 주소를 입력하면 예외가 발생한다. 입력 : {0}")
        @ValueSource(strings = {" ", "email", "email@"})
        void constructFail(final String invalidValue) {
            assertThatThrownBy(() -> new Email(invalidValue))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("올바른 이메일 주소가 아닙니다.");
        }
    }
}