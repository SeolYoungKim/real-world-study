package com.realworld.study.post.presentation;

import com.realworld.study.post.application.PostService;
import com.realworld.study.post.dto.request.PostRequest;
import com.realworld.study.post.dto.response.PostUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private static final String REDIRECT_URL = "/api/posts/%d";
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<Void> create(PostRequest postRequest) {
        PostUrlResponse postUrlResponse = postService.create(postRequest);
        String url = String.format(REDIRECT_URL, postUrlResponse.getId());

        return ResponseEntity
                .created(URI.create(url))
                .build();
    }
}