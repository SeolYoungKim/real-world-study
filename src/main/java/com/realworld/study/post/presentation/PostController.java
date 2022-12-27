package com.realworld.study.post.presentation;

import com.realworld.study.post.application.PostService;
import com.realworld.study.post.application.dto.PostDeleteResponse;
import com.realworld.study.post.application.dto.PostResponse;
import com.realworld.study.post.presentation.dto.PostCreateRequest;
import com.realworld.study.post.presentation.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController  //TODO Controller vs RestController
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public PostResponse createPost(@RequestBody final PostCreateRequest postCreateRequest,
            Authentication authentication) {
        return postService.createPost(postCreateRequest, authentication);
    }

    @PutMapping("/posts/{postId}")
    public PostResponse updatePost(@PathVariable final Long postId,
            @RequestBody final PostUpdateRequest postUpdateRequest, Authentication authentication) {
        return postService.updatePost(postId, postUpdateRequest, authentication);
    }

    @DeleteMapping("/posts/{postId}")
    public PostDeleteResponse deletePost(@PathVariable final Long postId) {
        return postService.deletePost(postId);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse getPost(@PathVariable final Long postId) {
        return postService.getPost(postId);
    }

    @GetMapping("/posts")
    public Page<PostResponse> getPosts(Pageable pageable) {
        return postService.getPosts(pageable);
    }
}
