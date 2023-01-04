package com.realworld.study.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realworld.study.auth.handler.dto.AccessTokenResponse;
import com.realworld.study.auth.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String accessToken = jwtProvider.accessToken(authentication);
        response.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
//        response.sendRedirect("/api/posts");  // Redirect 해버릴 경우 받는 측(프론트)에서 토큰을 못잡아낼 것 같음
        objectMapper.writeValue(response.getWriter(), new AccessTokenResponse(accessToken));
    }
}
