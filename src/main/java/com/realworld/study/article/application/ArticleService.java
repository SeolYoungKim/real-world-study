package com.realworld.study.article.application;

import com.realworld.study.article.application.dto.ArticleRequest;
import com.realworld.study.article.application.dto.ArticleUpdateRequest;
import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.ArticleRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article save(final ArticleRequest articleRequest) {
        return articleRepository.save(articleRequest.toEntity());
    }

    public void update(final Long id, final ArticleUpdateRequest articleUpdateRequest) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            if (articleUpdateRequest.getTitle() != null) {
                article.get().setTitle(articleUpdateRequest.getTitle());
            }
            if (articleUpdateRequest.getDescription() != null) {
                article.get().setDescription(articleUpdateRequest.getDescription());
            }
            if (articleUpdateRequest.getBody() != null) {
                article.get().setBody(articleUpdateRequest.getBody());
            }

            articleRepository.save(article.get());
        }
    }

    public void delete(final Long id) {
        articleRepository.deleteById(id);
    }
}
