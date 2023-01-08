package com.realworld.study.article.presentation;

import com.realworld.study.article.application.ArticleQueryService;
import com.realworld.study.article.application.ArticleService;
import com.realworld.study.article.application.dto.ArticleCreateRequest;
import com.realworld.study.article.application.dto.ArticleUpdateRequest;
import com.realworld.study.article.domain.Article;
import com.realworld.study.article.presentation.dto.ArticleResponse;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleQueryService articleQueryService;

    @PostMapping
    public ArticleResponse createArticle(final Principal principal,
            @RequestBody final ArticleCreateRequest articleCreateRequest) {
        String userEmail = principal.getName();

        Article article = articleService.createArticle(userEmail, articleCreateRequest);
        return ArticleResponse.of(article);
    }

    @GetMapping
    public Page<ArticleResponse> listArticles(final Pageable pageable) {
        return articleQueryService
                .getArticles(pageable)
                .map(ArticleResponse::of);
    }

    @GetMapping("/{id}")
    public ArticleResponse getArticle(@PathVariable final Long id) {
        return ArticleResponse.of(articleService.getArticle(id));
    }

    @PutMapping("/{id}")
    public ArticleResponse updateArticle(final Principal principal,
            @PathVariable final Long id,
            @RequestBody final ArticleUpdateRequest articleUpdateRequest) {
        String userEmail = principal.getName();
        Article updated = articleService.updateArticle(userEmail, id, articleUpdateRequest);
        return ArticleResponse.of(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(final Principal principal,
            @PathVariable final Long id) {
        String userEmail = principal.getName();
        articleService.deleteArticle(userEmail, id);
    }
}
