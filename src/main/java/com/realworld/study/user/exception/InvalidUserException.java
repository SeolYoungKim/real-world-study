package com.realworld.study.user.exception;

import com.realworld.study.common.exception.BadRequestException;
import com.realworld.study.common.exception.ErrorType;

public class InvalidUserException extends BadRequestException {

    public InvalidUserException(final ErrorType errorType) {
        super(errorType);
    }
}
