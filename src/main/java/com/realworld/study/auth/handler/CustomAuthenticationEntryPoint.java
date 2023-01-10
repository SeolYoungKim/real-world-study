package com.realworld.study.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realworld.study.exception.presentation.dto.ExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * TODO: 커스텀 예외 메세지가 출력이 안됨.
 *  - JwtNotAvailable을 AuthenticationException 상속해서 도전해봄
 *  - AuthenticationException을 직접 구현해서 메세지를 넣어봄
 *  - 아마 예외를 잡지는 못하는 것 같음 (try-catch 방식은 아닌건가?)
 *  - 예외가 발생하면 트리거되는 포인트인듯 -> 더 알아볼만 한 것 같음
 *  - 결론적으로 얘는 아직 커스터마이징을 하는 방법을 잘 몰라서, 그냥 Exception 핸들링을 위한 필터를 추가함
 *  - 얘가 어떻게 동작하길래...
 */
@Slf4j
@RequiredArgsConstructor
//@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.name(),
                authException.getMessage());

        responseSetup(response);
        objectMapper.writeValue(response.getWriter(), exceptionResponse);
    }

    private void responseSetup(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
    }
}
