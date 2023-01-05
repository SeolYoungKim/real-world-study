package com.realworld.study.post.application;

import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.repository.PostRepository;
import com.realworld.study.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostFeedService {

    private final PostRepository postRepository;

    public PostResponse read(Long postId) {
        Post post = findPostById(postId);

        return createPostResponse(post);
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    private PostResponse createPostResponse(Post post) {
        return new PostResponse(
            post.getId(),
            post.getTitle(),
            post.getBody(),
            post.getCreatedAt(),
            post.getUpdatedAt()
        );
    }
}