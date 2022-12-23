package com.realworld.study.post.presentation;

import com.realworld.study.post.application.PostFeedService;
import com.realworld.study.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostFeedController {

    private final PostFeedService postFeedService;

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> read(@PathVariable Long postId) {
        return ResponseEntity.ok(postFeedService.read(postId));
    }
}