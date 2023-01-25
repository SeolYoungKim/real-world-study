package com.realworld.study.article.exception;

import com.realworld.study.common.exception.BadRequestException;
import com.realworld.study.common.exception.ErrorType;

public class InvalidFavoriteArticleException extends BadRequestException {

    public InvalidFavoriteArticleException(final ErrorType errorType) {
        super(errorType);
    }
}
