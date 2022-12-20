package com.realworld.study.article.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realworld.study.article.application.dto.ArticleCreateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleCreateRequestWrapper {

    @JsonProperty("article")
    private ArticleCreateRequest articleCreateRequest;
}
