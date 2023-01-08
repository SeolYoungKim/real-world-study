package com.realworld.study.comment.application;

import com.realworld.study.auth.util.UsernamePasswordAuthUtils;
import com.realworld.study.comment.domain.Comment;
import com.realworld.study.comment.domain.CommentRepository;
import com.realworld.study.comment.presentation.dto.CommentCreateRequest;
import com.realworld.study.exception.domain.MemberNotFoundException;
import com.realworld.study.exception.domain.PostNotFoundException;
import com.realworld.study.member.domain.Email;
import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

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
}
