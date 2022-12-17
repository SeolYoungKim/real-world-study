package com.realworld.study.article.application;

import com.realworld.study.article.application.dto.ArticleRequest;
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
}
