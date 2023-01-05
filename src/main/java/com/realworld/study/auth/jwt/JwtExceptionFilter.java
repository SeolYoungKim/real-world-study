package com.realworld.study.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realworld.study.exception.presentation.dto.ExceptionResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (JwtNotAvailable e) {
            ExceptionResponse exceptionResponse = new ExceptionResponse(
                    HttpStatus.UNAUTHORIZED.name(), e.getMessage());

            responseSetup(response);
            objectMapper.writeValue(response.getWriter(), exceptionResponse);
        }
    }

    private void responseSetup(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
    }
}
