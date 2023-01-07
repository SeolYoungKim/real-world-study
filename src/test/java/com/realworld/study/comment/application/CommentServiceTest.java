package com.realworld.study.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.realworld.study.auth.FakeAuthentication;
import com.realworld.study.comment.domain.CommentRepository;
import com.realworld.study.comment.presentation.dto.CommentCreateRequest;
import com.realworld.study.exception.domain.MemberNotFoundException;
import com.realworld.study.exception.domain.PostNotFoundException;
import com.realworld.study.member.domain.Email;
import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CommentRepository commentRepository;

    private CommentService commentService;
    private Post post;
    private Member member;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(postRepository, memberRepository, commentRepository);

        member = Member.builder()
                .memberName("kim")
                .email("email@email.com")
                .password("12345678")
                .build();

        post = new Post("title", "contents", member);
    }

    @DisplayName("댓글을 생성할 때")
    @Nested
    class Create {
        @DisplayName("정상적으로 새로운 댓글이 생성된다.")
        @Test
        void createSuccess() {
            Long postId = 100L;
            when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));
            when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(member));

            String body = "body";
            CommentCreateRequest commentCreateRequest = new CommentCreateRequest(body);
            CommentResponse commentResponse = commentService.createComment(postId,
                    commentCreateRequest, new FakeAuthentication());

            assertThat(commentResponse.getBody()).isEqualTo(body);
        }

        @DisplayName("없는 게시글 번호가 전달될 경우 예외를 발생시킨다.")
        @Test
        void failByPostId() {
            when(postRepository.findById(any(Long.class))).thenReturn(Optional.empty());

            Long invalidPostId = Long.MIN_VALUE;
            CommentCreateRequest commentCreateRequest = new CommentCreateRequest("body");
            assertThatThrownBy(() -> commentService
                    .createComment(invalidPostId, commentCreateRequest, new FakeAuthentication()))
                    .isInstanceOf(PostNotFoundException.class);
        }

        @DisplayName("가입되지 않은 회원이 전달될 경우 예외를 발생시킨다.")
        @Test
        void failByMember() {
            when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));
            when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());

            Long postId = 100L;
            CommentCreateRequest commentCreateRequest = new CommentCreateRequest("body");
            assertThatThrownBy(() -> commentService
                    .createComment(postId, commentCreateRequest, new FakeAuthentication()))
                    .isInstanceOf(MemberNotFoundException.class);
        }
    }
}