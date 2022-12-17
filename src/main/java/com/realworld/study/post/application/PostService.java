package com.realworld.study.post.application;

import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostBody;
import com.realworld.study.post.domain.PostDescription;
import com.realworld.study.post.domain.PostTitle;
import com.realworld.study.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post create() {
        Post post = new Post(
            1L,
            new PostTitle(""),
            new PostDescription(""),
            new PostBody(""),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        return postRepository.save(post);
    }
}
