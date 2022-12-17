package com.realworld.study.article.application;

import com.realworld.study.article.application.dto.ArticleRequest;
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

    public void save(final ArticleRequest articleRequest) {
        articleRepository.save(articleRequest.toEntity());
    }

    public void update(final Long id, final ArticleUpdateRequest articleUpdateRequest) {
        Article article = articleRepository.getReferenceById(id);
        if (articleUpdateRequest.getTitle() != null) {
            article.setTitle(articleUpdateRequest.getTitle());
        }
        if (articleUpdateRequest.getDescription() != null) {
            article.setDescription(articleUpdateRequest.getDescription());
        }
        if (articleUpdateRequest.getBody() != null) {
            article.setBody(articleUpdateRequest.getBody());
        }
        articleRepository.save(article);
    }

    public void delete(final Long id) {
        articleRepository.deleteById(id);
    }
}
