package com.realworld.study.post.application;

import com.realworld.study.auth.util.UsernamePasswordAuthUtils;
import com.realworld.study.exception.domain.IsNotAuthorException;
import com.realworld.study.exception.domain.MemberNotFoundException;
import com.realworld.study.exception.domain.PostNotFoundException;
import com.realworld.study.member.domain.Email;
import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import com.realworld.study.post.application.dto.PostDeleteResponse;
import com.realworld.study.post.application.dto.PostResponse;
import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostQueryRepository;
import com.realworld.study.post.domain.PostRepository;
import com.realworld.study.post.presentation.dto.PostCreateRequest;
import com.realworld.study.post.presentation.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional  //TODO 이 애노테이션이 필요한 이유와 기전
@Service  //TODO CGLIB (PROXY) 상속
public class PostService {
    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final MemberRepository memberRepository;

    public PostResponse createPost(final PostCreateRequest postCreateRequest,
            final Authentication authentication) {
        Member member = findMemberBy(authentication);

        Post post = createPostBy(postCreateRequest, member);
        postRepository.save(post);

        return PostResponse.from(post);
    }

    private Post createPostBy(final PostCreateRequest dto, final Member member) {
        return new Post(dto.getTitle(), dto.getContents(), member);
    }

    public PostResponse updatePost(final Long postId, final PostUpdateRequest postUpdateRequest,
            final Authentication authentication) {
        Post post = findPostBy(postId);
        validateAuthor(post, authentication);

        post.update(postUpdateRequest.getTitle(), postUpdateRequest.getContents());

        return PostResponse.from(post);
    }

    public PostDeleteResponse deletePost(final Long postId, final Authentication authentication) {
        Post post = findPostBy(postId);
        validateAuthor(post, authentication);

        postRepository.delete(post);
        return new PostDeleteResponse(true);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(final Long postId) {
        Post post = findPostBy(postId);
        return PostResponse.from(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getPosts(final Pageable pageable) {
        return postQueryRepository.pagedPosts(pageable);
    }

    private Post findPostBy(final Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    private void validateAuthor(final Post post, final Authentication authentication) {
        Member member = findMemberBy(authentication);
        if (!post.isWrittenBy(member)) {
            throw new IsNotAuthorException();
        }
    }

    private Member findMemberBy(final Authentication authentication) {
        String email = UsernamePasswordAuthUtils.getEmail(authentication);
        return memberRepository.findByEmail(new Email(email))
                .orElseThrow(MemberNotFoundException::new);
    }
}
