package com.realworld.study.auth.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.realworld.study.auth.FakeAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {
    private static final String SECRET_KEY = "dGVzdHRlc3RzZXRzZXRzZXRzZXRzZXRzZXRsa2FzZGpma2xqYXNka2xmamF3ZWZrbGphd2xlZmhoaDMKdGVzdHRlc3RzZXRzZXRzZXRzZXRzZXRzZXRsa2FzZGpma2xqYXNka2xmamF3ZWZrbGphd2xlZmhoaDMK";
    private static final long EXPIRE_TIME = 3000000;
    private static final Pattern REGEX_FOR_JWT = Pattern.compile(
            "^[a-zA-Z0-9-_]*\\.[a-zA-Z0-9-_]*\\.[a-zA-Z0-9-_]*$");
    private static final String FAKE_BEARER_TOKEN = "Bearer jwt.jwt.jwt";

    @Mock
    private HttpServletRequest httpServletRequest;
    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider(SECRET_KEY, EXPIRE_TIME);
    }

    @DisplayName("인증 객체를 받으면 JWT를 발급한다.")
    @Test
    void accessToken() {
        String accessToken = jwtProvider.accessToken(new FakeAuthentication());
        boolean actual = accessToken.matches(REGEX_FOR_JWT.pattern());
        assertThat(actual).isTrue();
    }

    @DisplayName("HttpServletRequest로 넘어온 헤더를 파싱할 때")
    @Nested
    class resolveTokenFrom {
        @DisplayName("올바른 타입의 토큰이 들어왔을 경우 JWT만 파싱한 결과를 반환한다.")
        @Test
        void success() {
            when(httpServletRequest.getHeader(any(String.class))).thenReturn(FAKE_BEARER_TOKEN);

            String actual = jwtProvider.resolveTokenFrom(httpServletRequest);
            String expected = "jwt.jwt.jwt";
            assertThat(actual).isEqualTo(expected);
        }

        @DisplayName("올바르지 않은 토큰이 들어왔을 때")
        @Nested
        class fail {
            private final String blank = "";

            @DisplayName("토큰 타입이 Bearer가 아닌 경우")
            @Test
            void notBearerToken() {
                String basicToken = "Basic jwt.jwt.jwt";
                when(httpServletRequest.getHeader(any(String.class))).thenReturn(basicToken);

                String actual = jwtProvider.resolveTokenFrom(httpServletRequest);
                assertThat(actual).isEqualTo(blank);
            }

            @ParameterizedTest(name = "토큰이 아예 넘어오지 않았을 경우")
            @NullAndEmptySource
            void blankToken(String nullOrEmptyToken) {
                when(httpServletRequest.getHeader(any(String.class))).thenReturn(nullOrEmptyToken);

                String actual = jwtProvider.resolveTokenFrom(httpServletRequest);
                assertThat(actual).isEqualTo(blank);
            }
        }
    }
}