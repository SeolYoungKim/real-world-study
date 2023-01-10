package com.realworld.study.comment.application;

import com.realworld.study.auth.util.UsernamePasswordAuthUtils;
import com.realworld.study.comment.application.dto.CommentDeleteResponse;
import com.realworld.study.comment.application.dto.CommentResponse;
import com.realworld.study.comment.domain.Comment;
import com.realworld.study.comment.domain.CommentQueryRepository;
import com.realworld.study.comment.domain.CommentRepository;
import com.realworld.study.comment.presentation.dto.CommentCreateRequest;
import com.realworld.study.exception.domain.CommentNotFoundException;
import com.realworld.study.exception.domain.IsNotAuthorException;
import com.realworld.study.exception.domain.MemberNotFoundException;
import com.realworld.study.exception.domain.PostNotFoundException;
import com.realworld.study.member.domain.Email;
import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;

    public CommentResponse createComment(final Long postId,
            final CommentCreateRequest commentCreateRequest,
            final Authentication authentication
    ) {
        Comment comment = new Comment(commentCreateRequest.getBody(),
                findPostBy(postId), findMemberBy(authentication));
        commentRepository.save(comment);

        return CommentResponse.from(comment);
    }

    private Post findPostBy(final Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    //TODO 중복, 중복, 중복!!
    private Member findMemberBy(final Authentication authentication) {
        String email = UsernamePasswordAuthUtils.getEmail(authentication);
        return memberRepository.findByEmail(new Email(email))
                .orElseThrow(MemberNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getComments(final Long postId, final Pageable pageable) {
        validatePostId(postId);
        return commentQueryRepository.pagedComments(postId, pageable);
    }

    private void validatePostId(final Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
    }

    public CommentDeleteResponse deleteComment(final Long postId, final Long commentsId,
            final Authentication authentication) {
        validatePostId(postId);

        Comment comment = findCommentBy(commentsId);
        validateAuthor(comment, authentication);

        commentRepository.delete(comment);
        return new CommentDeleteResponse(true);
    }

    private Comment findCommentBy(final Long commentsId) {
        return commentRepository.findById(commentsId)
                .orElseThrow(CommentNotFoundException::new);
    }

    private void validateAuthor(final Comment comment, final Authentication authentication) {
        Member member = findMemberBy(authentication);
        if (!comment.writtenBy(member)) {
            throw new IsNotAuthorException();
        }
    }
}
