package com.realworld.study.post.domain;

import static com.realworld.study.post.domain.QPost.post;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realworld.study.post.application.dto.PostResponse;
import com.realworld.study.post.application.dto.QPostResponse;
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

    public Page<PostResponse> pagedPosts(Pageable pageable) {
        List<PostResponse> postResponses = queryFactory.select(new QPostResponse(post.id,
                        post.title,
                        post.contents,
                        post.author.memberName.as("author"),
                        post.createdAt,
                        post.modifiedAt))
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
