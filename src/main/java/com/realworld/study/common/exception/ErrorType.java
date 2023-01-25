package com.realworld.study.common.exception;

import lombok.Getter;

@Getter
public enum ErrorType {

    FORBIDDEN(1001, "권한이 없습니다."),
    NOT_AUTHENTICATED(1002, "인증이 필요합니다."),
    INVALID_TOKEN(1003, "유효하지 않은 토큰입니다."),

    NOT_FOUND_ARTICLE(2001, "존재하지 않는 게시글 입니다."),
    ALREADY_FAVORITED_ARTICLE(2002, "이미 좋아요한 게시글 입니다."),
    NOT_FAVORITED_ARTICLE(2003, "게시글에 좋아요를 하지 않았습니다."),

    NOT_FOUND_USER(3001, "존재하지 않는 유저 입니다."),

    NOT_FOUND_COMMENT(4001, "존재하지 않는 댓글 입니다."),

    REQUEST_EXCEPTION(9001, "잘못된 요청 입니다."),
    UNHANDLED_EXCEPTION(9999, "예상치 못한 에러 입니다.");

    private final int code;
    private final String message;

    ErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
