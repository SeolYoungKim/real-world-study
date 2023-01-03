package com.realworld.study.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtProvider.resolveTokenFrom(request);
        if (StringUtils.hasText(jwt)) {
            setAuthenticationOrNot(jwt);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthenticationOrNot(String accessToken) {
        if (jwtProvider.validateJwt(accessToken)) {
            Authentication authentication = jwtProvider.authentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return;
        }

        logger.info("JWT 미인증 사용자 입니다.");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().equals("/login");
    }
}
