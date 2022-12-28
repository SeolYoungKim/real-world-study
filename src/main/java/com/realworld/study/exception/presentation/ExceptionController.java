package com.realworld.study.exception.presentation;

import com.realworld.study.exception.domain.NotFoundException;
import com.realworld.study.exception.domain.RealWorldException;
import com.realworld.study.exception.presentation.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ExceptionResponse notFoundExceptionHandler(NotFoundException e) {
        return new ExceptionResponse(e.getCode(), e.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RealWorldException.class)
    public ExceptionResponse realWorldExceptionHandler(RealWorldException e) {
        return new ExceptionResponse(e.getCode(), e.getMessage());
    }
}
