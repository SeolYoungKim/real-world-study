package com.realworld.study.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {
    private static final Pattern EMAIL_REGEX = Pattern.compile(  // TODO 잘 잡아내는 정규식을 찾지 못함. 아마 이메일 인증으로 땜빵하거나 OAuth2를 써야 할듯 합니다.
            "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    @Column(name = "email",unique = true, nullable = false)
    private String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!StringUtils.hasText(value) || !isEmailAddress(value)) {
            throw new IllegalArgumentException("올바른 이메일 주소가 아닙니다.");
        }
    }

    private boolean isEmailAddress(String value) {
        return EMAIL_REGEX.matcher(value).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Email{" +
                "value='" + value + '\'' +
                '}';
    }
}
