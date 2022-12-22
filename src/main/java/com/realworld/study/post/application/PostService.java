package com.realworld.study.post.application;

import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostRepository;
import com.realworld.study.post.presentation.dto.PostCreateRequest;
import com.realworld.study.post.presentation.dto.PostUpdateRequest;
import com.realworld.study.post.application.dto.PostDeleteResponse;
import com.realworld.study.post.application.dto.PostResponse;
import com.realworld.study.user.domain.Member;
import com.realworld.study.user.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional  //TODO 이 애노테이션이 필요한 이유와 기전
@Service  //TODO CGLIB (PROXY) 상속
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostResponse createPost(final PostCreateRequest postCreateRequest, final Member member) {
        //TODO 원래는 인증 객체에서 member 식별자를 꺼내서, memberRepository에서 멤버를 조회해서 사용해야 한다.
        // 현재는 인증을 구현하지 않아 아래의 방식으로 대체한다.
        memberRepository.save(member);

        Post post = getPostBy(postCreateRequest, member);
        postRepository.save(post);

        return PostResponse.from(post);
    }

    private Post getPostBy(final PostCreateRequest dto, final Member member) {
        return new Post(dto.getTitle(), dto.getContents(), member);
    }

    public PostResponse updatePost(final Long postId, final PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글 입니다."));

        post.update(postUpdateRequest.getTitle(), postUpdateRequest.getContents());  //TODO 이 부분의 DTO 의존성을 없앨 수는 없을까?
        return PostResponse.from(post);
    }

    public PostDeleteResponse deletePost(final Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글 입니다."));

        postRepository.delete(post);
        return new PostDeleteResponse(true);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글 입니다."));

        return PostResponse.from(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.pagedPosts(pageable);
        return posts.map(PostResponse::from);
    }
}
