package com.realworld.study.common.exception.dto;

import com.realworld.study.common.exception.CustomException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ErrorResponse {

    private int errorCode;
    private String message;

    public ErrorResponse(final int errorCode, final String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ErrorResponse of(final CustomException e) {
        return new ErrorResponse(e.getCode(), e.getMessage());
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}
