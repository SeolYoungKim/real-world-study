package com.realworld.study.comment.domain;

import static com.realworld.study.comment.domain.QComment.comment;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realworld.study.comment.application.dto.CommentResponse;
import com.realworld.study.comment.application.dto.QCommentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Page<CommentResponse> pagedComments(final Long postId, final Pageable pageable) {
        List<CommentResponse> commentResponses = queryFactory.select(new QCommentResponse(comment.id,
                        comment.body,
                        comment.author.memberName.as("author"),
                        comment.createdAt,
                        comment.modifiedAt))
                .from(comment)
                .where(comment.post.id.eq(postId))
                .orderBy(comment.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(comment.count())
                .from(comment);

        return PageableExecutionUtils.getPage(commentResponses, pageable, countQuery::fetchOne);
    }
}
