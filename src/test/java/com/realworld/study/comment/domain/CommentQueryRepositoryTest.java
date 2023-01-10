package com.realworld.study.comment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.realworld.study.comment.application.dto.CommentResponse;
import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostRepository;
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
class CommentQueryRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentQueryRepository commentQueryRepository;

    private final int commentNumbers = 20;
    private Post post;

    @BeforeEach
    void setUp() {
        Member fakeMember = new Member("email@domain.com", "12345678", "kim", "my name is...", "image");
        memberRepository.save(fakeMember);

        post = new Post("title", "contents", fakeMember);
        postRepository.save(post);

        List<Comment> comments = IntStream.rangeClosed(1, commentNumbers)
                .mapToObj(i -> new Comment("body" + i, post, fakeMember))
                .toList();

        commentRepository.saveAll(comments);
    }

    @DisplayName("전체 댓글을 조회할 때")
    @Nested
    class GetComments {
        @DisplayName("Pagination이 적용된다.")
        @Test
        void pagingTest() {
            Pageable pageable = PageRequest.of(0, 5);
            Page<CommentResponse> page = commentQueryRepository.pagedComments(post.getId(),
                    pageable);

            assertThat(page.getTotalPages()).isEqualTo(4);
            assertThat(page.getTotalElements()).isEqualTo(commentNumbers);
        }

        @DisplayName("댓글들이 id 내림차순으로 정렬된다.")
        @Test
        void descTest() {
            Pageable pageable = PageRequest.of(0, 5);
            Page<CommentResponse> page = commentQueryRepository.pagedComments(post.getId(),
                    pageable);

            List<CommentResponse> commentResponses = page.toList();
            CommentResponse commentResponse = commentResponses.get(0);

            assertThat(commentResponse.getBody()).isEqualTo("body" + commentNumbers);
        }

        @DisplayName("없는 postId의 댓글들을 조회할 경우 비어있는 객체가 반환된다.")
        @Test
        void empty() {
            Pageable pageable = PageRequest.of(0, 5);
            Page<CommentResponse> page = commentQueryRepository.pagedComments(Long.MIN_VALUE,
                    pageable);

            assertThat(page).isEmpty();
        }
    }
}