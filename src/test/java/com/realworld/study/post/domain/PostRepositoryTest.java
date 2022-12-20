package com.realworld.study.post.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.realworld.study.post.TestConfig;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Import(TestConfig.class)
@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    private final int postNumbers = 20;

    @BeforeEach
    void setUp() {
        List<Post> posts = IntStream.rangeClosed(1, postNumbers)
                .mapToObj(i -> new Post("title" + i, "contents" + i))
                .toList();
        postRepository.saveAll(posts);
    }

    @DisplayName("전체 게시글을 조회할 때 ")
    @Nested
    class getPosts {
        @DisplayName("Pagination이 적용된다.")
        @Test
        void pagingTest() {
            Pageable pageable = PageRequest.of(0, 5);
            Page<Post> page = postRepository.pagedPosts(pageable);

            assertThat(page.getTotalPages()).isEqualTo(4);
            assertThat(page.getTotalElements()).isEqualTo(postNumbers);
        }

        @DisplayName("게시글들이 id 내림차순으로 정렬된다.")
        @Test
        void descTest() {
            Pageable pageable = PageRequest.of(0, 5);
            Page<Post> page = postRepository.pagedPosts(pageable);

            List<Post> posts = page.toList();
            Post post = posts.get(0);

            assertThat(post.getTitle()).isEqualTo("title" + postNumbers);
        }
    }
}