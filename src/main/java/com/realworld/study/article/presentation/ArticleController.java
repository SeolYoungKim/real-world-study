package com.realworld.study.article.presentation;

import com.realworld.study.article.application.ArticleService;
import com.realworld.study.article.domain.Article;
import com.realworld.study.article.presentation.dto.ArticleCreateRequestWrapper;
import com.realworld.study.article.presentation.dto.ArticleCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/")
    public ResponseEntity<ArticleCreateResponse> createArticle(
            @RequestBody final ArticleCreateRequestWrapper articleCreateRequestWrapper) {
        Article article = articleService.createArticle(
                articleCreateRequestWrapper.getArticleCreateRequest());

        return ResponseEntity.ok(ArticleCreateResponse.of(article));
    }
}
