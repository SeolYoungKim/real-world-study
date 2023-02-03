package com.realworld.study.article.exception;

import com.realworld.study.common.exception.BadRequestException;
import com.realworld.study.common.exception.ErrorType;

public class InvalidCommentException extends BadRequestException {

    public InvalidCommentException(final ErrorType errorType) {
        super(errorType);
    }
}
