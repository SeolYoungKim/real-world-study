package com.realworld.study.article.application;

import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.ArticleQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleQueryService {

    private final ArticleQueryRepository articleQueryRepository;

    public Page<Article> getArticles(final Pageable pageable) {
        List<Article> articles = articleQueryRepository.findAll(pageable);

        return PageableExecutionUtils.getPage(articles, pageable, () -> articles.size());
    }
}
