package com.realworld.study.post.application;

import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostBody;
import com.realworld.study.post.domain.PostDescription;
import com.realworld.study.post.domain.PostTitle;
import com.realworld.study.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post create() {
        Post savedPost = createPost();
        // savedPost -> response dto
        return savedPost;
    }

    private Post createPost() {
        Post post = new Post(
            new PostTitle("Post Title"),
            new PostDescription("Post Description"),
            new PostBody("Post Body"),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        return postRepository.save(post);
    }

    public Post update() {
        Optional<Post> post = postRepository.findById(1L);
        post.get().updateTitle("Post Title Updated");
        return post.get();
    }
}
