package com.realworld.study.post.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.realworld.study.auth.FakeAuthentication;
import com.realworld.study.exception.domain.IsNotAuthorException;
import com.realworld.study.exception.domain.MemberNotFoundException;
import com.realworld.study.exception.domain.PostNotFoundException;
import com.realworld.study.member.domain.Email;
import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import com.realworld.study.post.application.dto.PostDeleteResponse;
import com.realworld.study.post.application.dto.PostResponse;
import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostQueryRepository;
import com.realworld.study.post.domain.PostRepository;
import com.realworld.study.post.presentation.dto.PostCreateRequest;
import com.realworld.study.post.presentation.dto.PostUpdateRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
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
class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private PostQueryRepository postQueryRepository;

    @Mock
    private MemberRepository memberRepository;

    private PostService postService;
    private Member author;

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository, postQueryRepository, memberRepository);
        author = Member.builder()
                .email("email@domain.com")
                .password("12345678")
                .memberName("kim")
                .build();
    }

    @DisplayName("게시글을 생성할 때")
    @Nested
    class create {
        private final String title = "제목";
        private final String contents = "내용";

        @DisplayName("정상적으로 새로운 게시글이 생성된다.")
        @Test
        void createSuccess() {
            when(memberRepository.findByEmail(any(Email.class))).thenReturn(
                    Optional.of(author));

            PostCreateRequest postCreateRequest = new PostCreateRequest(title, contents);
            PostResponse postResponse = postService.createPost(postCreateRequest,
                    new FakeAuthentication());

            assertThat(postResponse.getTitle()).isEqualTo(title);
            assertThat(postResponse.getContents()).isEqualTo(contents);
        }

        @DisplayName("가입되지 않은 회원이 전달될 경우 예외를 발생시킨다")
        @Test
        void failByMember() {
            when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());

            PostCreateRequest postCreateRequest = new PostCreateRequest(title, contents);
            assertThatThrownBy(() -> postService
                    .createPost(postCreateRequest, new FakeAuthentication()))
                    .isInstanceOf(MemberNotFoundException.class);
        }
    }

    @DisplayName("게시글을 수정할 때")
    @Nested
    class update {
        private final Long anyPostId = 100L;
        private Post post;
        private PostUpdateRequest postUpdateRequest;

        @BeforeEach
        void setUp() {
            String titleBeforeUpdate = "제목";
            String contentsBeforeUpdate = "내용";
            post = new Post(titleBeforeUpdate, contentsBeforeUpdate, author);

            String updatedTitle = "title";
            String updatedContents = "contents";
            postUpdateRequest = new PostUpdateRequest(updatedTitle, updatedContents);
        }

        @DisplayName("아이디에 해당되는 게시글이 있는 경우 정상적으로 게시글이 수정된다.")
        @Test
        void updateSuccess() {
            when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));
            when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(
                    author));

            PostResponse postResponse = postService.updatePost(anyPostId, postUpdateRequest,
                    new FakeAuthentication());

            String updatedTitle = "title";
            String updatedContents = "contents";

            assertThat(postResponse.getTitle()).isEqualTo(updatedTitle);
            assertThat(postResponse.getContents()).isEqualTo(updatedContents);
        }

        @DisplayName("아이디에 해당되는 게시글이 없는 경우 예외가 발생한다.")
        @Test
        void updateFailByPostNotFound() {
            when(postRepository.findById(any(Long.class))).thenReturn(Optional.empty());

            assertThatThrownBy(() -> postService
                    .updatePost(anyPostId, postUpdateRequest, new FakeAuthentication()))
                    .isInstanceOf(PostNotFoundException.class)
                    .hasMessageContaining("없는 게시글 입니다.");
        }

        @DisplayName("게시글의 작성자가 아닐 경우 예외가 발생한다.")
        @Test
        void updateFailByIsNotAuthor() {
            Member notAuthor = Member.builder()
                    .email("notAuthor@email.com")
                    .memberName("park")
                    .password("12345678")
                    .build();

            when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));
            when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(notAuthor));

            assertThatThrownBy(() -> postService
                    .updatePost(anyPostId, postUpdateRequest, new FakeAuthentication()))
                    .isInstanceOf(IsNotAuthorException.class);
        }
    }

    @DisplayName("게시글을 삭제할 때")
    @Nested
    class delete {
        private final Long anyPostId = 100L;
        private Post post;

        @BeforeEach
        void setUp() {
            String title = "제목";
            String contents = "내용";
            post = new Post(title, contents, author);
        }

        @DisplayName("아이디에 해당하는 게시글이 있는 경우 게시글이 성공적으로 삭제된다.")
        @Test
        void deleteSuccess() {
            when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));
            when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(
                    author));

            PostDeleteResponse postDeleteResponse = postService.deletePost(anyPostId,
                    new FakeAuthentication());
            assertThat(postDeleteResponse.isDeleted()).isTrue();
        }

        @DisplayName("아이디에 해당하는 게시글이 없는 경우 예외가 발생한다.")
        @Test
        void deleteFail() {
            final String EXCEPTION_MESSAGE = "없는 게시글 입니다.";
            when(postRepository.findById(any(Long.class))).thenReturn(Optional.empty());

            assertThatThrownBy(() -> postService.deletePost(anyPostId, new FakeAuthentication()))
                    .isInstanceOf(PostNotFoundException.class)
                    .hasMessageContaining(EXCEPTION_MESSAGE);
        }

        @DisplayName("게시글의 작성자가 아닐 경우 예외가 발생한다.")
        @Test
        void deleteFailByIsNotAuthor() {
            Member notAuthor = Member.builder()
                    .email("notAuthor@email.com")
                    .memberName("park")
                    .password("12345678")
                    .build();

            when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));
            when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(notAuthor));

            assertThatThrownBy(() -> postService.deletePost(anyPostId, new FakeAuthentication()))
                    .isInstanceOf(IsNotAuthorException.class);
        }
    }

    @DisplayName("여러건의 게시글을 조회할 때")
    @Nested
    class read {
        @DisplayName("저장된 Post의 개수만큼 Page<PostResponse>를 반환한다")
        @Test
        void getPosts() {
            List<PostResponse> postResponsesForMock = LongStream.rangeClosed(1, 3)
                    .mapToObj(l -> PostResponse.builder()
                            .id(l)
                            .title("title" + l)
                            .contents("contents" + l)
                            .build())
                    .toList();

            Page<PostResponse> page = new PageImpl<>(postResponsesForMock);
            when(postQueryRepository.pagedPosts(any(Pageable.class))).thenReturn(page);

            Pageable pageable = PageRequest.of(0, 1);
            Page<PostResponse> result = postService.getPosts(pageable);

            List<PostResponse> postResponses = result.stream().toList();
            assertThat(postResponses).hasSize(3);
        }
    }
}