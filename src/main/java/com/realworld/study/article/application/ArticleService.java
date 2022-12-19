package com.realworld.study.article.application;

import com.realworld.study.article.application.dto.ArticleCreateRequest;
import com.realworld.study.article.application.dto.ArticleUpdateRequest;
import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article createArticle(final ArticleCreateRequest articleCreateRequest) {
        return articleRepository.save(articleCreateRequest.toEntity());
    }

    public void updateArticle(final Long id, final ArticleUpdateRequest articleUpdateRequest) {
        Article article = articleRepository.findById(id).orElseThrow();

        article.update(articleUpdateRequest.getTitle(),
                articleUpdateRequest.getDescription(),
                articleUpdateRequest.getBody());

        articleRepository.save(article);
    }

    public void deleteArticle(final Long id) {
        articleRepository.deleteById(id);
    }

    public Article getArticle(final Long id) {
        return articleRepository.findById(id).orElseThrow();
    }
}
