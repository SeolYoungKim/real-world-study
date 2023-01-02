package com.realworld.study.post.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import com.realworld.study.post.application.dto.PostResponse;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class PostQueryRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostQueryRepository postQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    private final int postNumbers = 20;

    @BeforeEach
    void setUp() {
        Member fakeMember = new Member("email@domain.com", "12345678", "kim", "my name is...", "image");
        memberRepository.save(fakeMember);

        List<Post> posts = IntStream.rangeClosed(1, postNumbers)
                .mapToObj(i -> new Post("title" + i, "contents" + i, fakeMember))
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
            Page<PostResponse> page = postQueryRepository.pagedPosts(pageable);

            assertThat(page.getTotalPages()).isEqualTo(4);
            assertThat(page.getTotalElements()).isEqualTo(postNumbers);
        }

        @DisplayName("게시글들이 id 내림차순으로 정렬된다.")
        @Test
        void descTest() {
            Pageable pageable = PageRequest.of(0, 5);
            Page<PostResponse> page = postQueryRepository.pagedPosts(pageable);

            List<PostResponse> postResponses = page.toList();
            PostResponse postResponse = postResponses.get(0);

            assertThat(postResponse.getTitle()).isEqualTo("title" + postNumbers);
        }
    }
}