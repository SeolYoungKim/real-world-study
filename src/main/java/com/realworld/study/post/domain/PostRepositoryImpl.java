package com.realworld.study.post.domain;

import static com.realworld.study.post.domain.QPost.post;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> pagedPosts(Pageable pageable) {
        List<Post> postResponses = queryFactory.select(post)
                .from(post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(post.count())
                .from(post);

        return PageableExecutionUtils.getPage(postResponses, pageable, countQuery::fetchOne);
    }
}
