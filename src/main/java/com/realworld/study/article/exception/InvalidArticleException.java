package com.realworld.study.article.exception;

import com.realworld.study.common.exception.BadRequestException;
import com.realworld.study.common.exception.ErrorType;

public class InvalidArticleException extends BadRequestException {

    public InvalidArticleException(final ErrorType errorType) {
        super(errorType);
    }
}
