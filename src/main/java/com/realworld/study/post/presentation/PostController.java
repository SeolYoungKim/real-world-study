package com.realworld.study.post.presentation;

import com.realworld.study.post.application.PostService;
import com.realworld.study.post.presentation.dto.request.PostCreateRequest;
import com.realworld.study.post.presentation.dto.request.PostUpdateRequest;
import com.realworld.study.post.presentation.dto.response.PostDeleteResponse;
import com.realworld.study.post.presentation.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public PostResponse createPost(@RequestBody PostCreateRequest postCreateRequest) {
        return postService.createPost(postCreateRequest);
    }

    @PutMapping("/posts/{postId}")
    public PostResponse updatePost(@PathVariable Long postId,
            @RequestBody PostUpdateRequest postUpdateRequest) {

        return postService.updatePost(postId, postUpdateRequest);
    }

    @DeleteMapping("/posts/{postId}")
    public PostDeleteResponse deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }
}
