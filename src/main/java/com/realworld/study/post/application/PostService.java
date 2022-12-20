package com.realworld.study.post.application;

import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post create() {
        return null;
    }

    private Post createPost() {
        return null;
    }
}
