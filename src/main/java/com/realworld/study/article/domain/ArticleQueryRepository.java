package com.realworld.study.article.domain;

import static com.realworld.study.article.domain.QArticle.article;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ArticleQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Article> findAll(Pageable pageable) {
        return jpaQueryFactory.selectFrom(article)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
