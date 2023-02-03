package com.realworld.study.auth.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realworld.study.common.exception.ErrorType;
import com.realworld.study.common.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authException) {
        log.warn("Authorize Fail");
        writeErrorResponse(response, HttpStatus.UNAUTHORIZED, new ErrorResponse(
                ErrorType.NOT_AUTHENTICATED.getCode(),
                ErrorType.NOT_AUTHENTICATED.getMessage()));
    }

    private void writeErrorResponse(final HttpServletResponse response,
            final HttpStatus httpStatus,
            final ErrorResponse errorResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(httpStatus.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            log.error("An exception occurred while writing the Error Response!", e);
        }
    }
}
