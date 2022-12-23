package com.realworld.study.post.domain;

import static com.realworld.study.post.domain.QPost.post;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostQueryRepository {
    private final JPAQueryFactory queryFactory;

    //TODO 프로젝션 or 상속 포기
    public Page<Post> pagedPosts(Pageable pageable) {
        List<Post> postResponses = queryFactory.select(post)
                .from(post)
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(post.count())
                .from(post);

        return PageableExecutionUtils.getPage(postResponses, pageable, countQuery::fetchOne);
    }
}
