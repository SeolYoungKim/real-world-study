package com.realworld.study.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.realworld.study.auth.FakeAuthentication;
import com.realworld.study.comment.application.dto.CommentDeleteResponse;
import com.realworld.study.comment.application.dto.CommentResponse;
import com.realworld.study.comment.domain.Comment;
import com.realworld.study.comment.domain.CommentQueryRepository;
import com.realworld.study.comment.domain.CommentRepository;
import com.realworld.study.comment.presentation.dto.CommentCreateRequest;
import com.realworld.study.exception.domain.CommentNotFoundException;
import com.realworld.study.exception.domain.IsNotAuthorException;
import com.realworld.study.exception.domain.MemberNotFoundException;
import com.realworld.study.exception.domain.PostNotFoundException;
import com.realworld.study.member.domain.Email;
import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentQueryRepository commentQueryRepository;

    private CommentService commentService;
    private Post post;
    private Member member;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(postRepository, memberRepository, commentRepository,
                commentQueryRepository);

        member = Member.builder()
                .memberName("kim")
                .email("email@email.com")
                .password("12345678")
                .build();

        post = new Post("title", "contents", member);
    }

    @DisplayName("댓글을 생성할 때")
    @Nested
    class create {
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

    @DisplayName("특정 게시글에 달린 댓글들을 조회할 때")
    @Nested
    class getComments {
        @DisplayName("존재하는 게시글의 댓글을 조회할 경우 정상적으로 조회된다.")
        @Test
        void success() {
            Page<CommentResponse> page = new PageImpl<>(
                    List.of(CommentResponse.from(new Comment("body", post, member))));

            when(postRepository.existsById(any(Long.class))).thenReturn(true);
            when(commentQueryRepository.pagedComments(any(Long.class), any(Pageable.class)))
                    .thenReturn(page);

            Long postId = 100L;
            Pageable pageable = PageRequest.of(0, 5);
            Page<CommentResponse> actual = commentService.getComments(postId, pageable);

            assertThat(actual.getTotalPages()).isEqualTo(1);
            assertThat(actual.getTotalElements()).isEqualTo(1);
        }

        @DisplayName("없는 게시글에 대한 댓글을 조회할 경우 예외를 발생시킨다.")
        @Test
        void fail() {
            when(postRepository.existsById(any(Long.class))).thenReturn(false);

            Long postId = 100L;
            Pageable pageable = PageRequest.of(0, 5);

            assertThatThrownBy(() -> commentService.getComments(postId, pageable))
                    .isInstanceOf(PostNotFoundException.class);
        }
    }

    @DisplayName("댓글을 삭제할 때")
    @Nested
    class deleteComment {
        @DisplayName("게시글이 존재하고 작성자인 경우 댓글이 삭제된다.")
        @Test
        void success() {
            Comment comment = new Comment("body", post, member);
            when(postRepository.existsById(any(Long.class))).thenReturn(true);
            when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(comment));
            when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(member));

            Long postId = 100L;
            Long commentsId = 100L;
            CommentDeleteResponse commentDeleteResponse = commentService.deleteComment(postId,
                    commentsId, new FakeAuthentication());

            assertThat(commentDeleteResponse.isDeleted()).isTrue();
        }

        @DisplayName("댓글 삭제를 실패하는 경우")
        @Nested
        class fail {
            @DisplayName("게시글이 존재하지 않는 경우 예외를 발생시킨다.")
            @Test
            void failCase1() {
                when(postRepository.existsById(any(Long.class))).thenReturn(false);

                Long postId = 100L;
                Long commentsId = 100L;
                assertThatThrownBy(() -> commentService
                        .deleteComment(postId, commentsId, new FakeAuthentication()))
                        .isInstanceOf(PostNotFoundException.class);
            }

            @DisplayName("없는 댓글인 경우 예외를 발생시킨다.")
            @Test
            void failCase2() {
                when(postRepository.existsById(any(Long.class))).thenReturn(true);
                when(commentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

                Long postId = 100L;
                Long commentsId = 100L;
                assertThatThrownBy(() -> commentService
                        .deleteComment(postId, commentsId, new FakeAuthentication()))
                        .isInstanceOf(CommentNotFoundException.class);
            }

            @DisplayName("없는 회원인 경우 예외를 발생시킨다.")
            @Test
            void failCase3() {
                Comment comment = new Comment("body", post, member);
                when(postRepository.existsById(any(Long.class))).thenReturn(true);
                when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(comment));
                when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());

                Long postId = 100L;
                Long commentsId = 100L;
                assertThatThrownBy(() -> commentService
                        .deleteComment(postId, commentsId, new FakeAuthentication()))
                        .isInstanceOf(MemberNotFoundException.class);
            }

            @DisplayName("댓글의 작성자가 아닌 경우 예외를 발생시킨다.")
            @Test
            void failCase4() {
                Comment comment = new Comment("body", post, member);
                when(postRepository.existsById(any(Long.class))).thenReturn(true);
                when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(comment));

                Member notAuthor = new Member("notAuthor@domain.com", "123123123", "park", "", "");
                when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(notAuthor));

                Long postId = 100L;
                Long commentsId = 100L;
                assertThatThrownBy(() -> commentService
                        .deleteComment(postId, commentsId, new FakeAuthentication()))
                        .isInstanceOf(IsNotAuthorException.class);
            }
        }
    }
}