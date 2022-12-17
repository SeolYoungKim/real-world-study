package com.realworld.study.post.application;


import com.realworld.study.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    
    @Test
    @DisplayName("Post 생성")
    void create() {
        Post post = postService.create();
        assertThat(post.getTitle()).isEqualTo("Post Title");
        assertThat(post.getDescription()).isEqualTo("Post Description");
        assertThat(post.getBody()).isEqualTo("Post Body");
    }
}