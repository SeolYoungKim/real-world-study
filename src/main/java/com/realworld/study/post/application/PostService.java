package com.realworld.study.post.application;

import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostRepository;
import com.realworld.study.post.presentation.dto.request.PostCreateRequest;
import com.realworld.study.post.presentation.dto.request.PostUpdateRequest;
import com.realworld.study.post.presentation.dto.response.PostDeleteResponse;
import com.realworld.study.post.presentation.dto.response.PostResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional  //TODO 이 애노테이션이 필요한 이유와 기전
@Service  //TODO CGLIB (PROXY) 상속
public class PostService {
    private final PostRepository postRepository;

    public PostResponse createPost(final PostCreateRequest postCreateRequest) {
        Post post = postCreateRequest.toPost();
        postRepository.save(post);

        return PostResponse.from(post);
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

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글 입니다."));

        return PostResponse.from(post);
    }

    public Page<PostResponse> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.pagedPosts(pageable);
        return posts.map(PostResponse::from);
    }
}
