package com.realworld.study.post.application;

import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostRepository;
import com.realworld.study.post.application.dto.PostCreateRequest;
import com.realworld.study.post.application.dto.PostUpdateRequest;
import com.realworld.study.post.presentation.dto.PostDeleteResponse;
import com.realworld.study.post.presentation.dto.PostResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;

    public PostResponse createPost(PostCreateRequest postCreateRequest) {
        Post post = new Post(postCreateRequest.getTitle(), postCreateRequest.getContents());
        postRepository.save(post);

        return PostResponse.from(post);
    }

    public PostResponse updatePost(Long postId, PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글 입니다."));

        post.update(postUpdateRequest.getTitle(), postUpdateRequest.getContents());
        return PostResponse.from(post);
    }

    public PostDeleteResponse deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글 입니다."));

        postRepository.delete(post);
        return new PostDeleteResponse(true);
    }
}
