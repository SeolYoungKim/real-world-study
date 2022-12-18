package com.realworld.study.post.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostRepository;
import com.realworld.study.post.presentation.dto.request.PostCreateRequest;
import com.realworld.study.post.presentation.dto.request.PostUpdateRequest;
import com.realworld.study.post.presentation.dto.response.PostDeleteResponse;
import com.realworld.study.post.presentation.dto.response.PostResponse;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO 단위 테스트란 ???? <-
 * //TODO 해당 애노테이션을 테스트 클래스에 작성하면 어떤 일이 벌어지나 ?
 */
@Transactional
@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    private PostService postService;

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository);
    }

    @DisplayName("게시글을 생성할 때")
    @Nested
    class save {
        @DisplayName("정상적으로 새로운 게시글이 생성된다.")
        @Test
        void createSuccess() {
            String title = "제목";
            String contents = "내용";
            Post post = new Post(title, contents);
            when(postRepository.save(any(Post.class))).thenReturn(post);

            PostCreateRequest postCreateRequest = new PostCreateRequest(title, contents);
            PostResponse postResponse = postService.createPost(postCreateRequest);

            assertThat(postResponse.getTitle()).isEqualTo(title);
            assertThat(postResponse.getContents()).isEqualTo(contents);
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
            post = new Post(titleBeforeUpdate, contentsBeforeUpdate);

            String updatedTitle = "title";
            String updatedContents = "contents";
            postUpdateRequest = new PostUpdateRequest(updatedTitle, updatedContents);
        }

        @DisplayName("아이디에 해당되는 게시글이 있는 경우 정상적으로 게시글이 수정된다.")
        @Test
        void updateSuccess() {
            when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));
            PostResponse postResponse = postService.updatePost(anyPostId, postUpdateRequest);

            String updatedTitle = "title";
            String updatedContents = "contents";

            assertThat(postResponse.getTitle()).isEqualTo(updatedTitle);
            assertThat(postResponse.getContents()).isEqualTo(updatedContents);
        }

        @DisplayName("아이디에 해당되는 게시글이 없는 경우 예외가 발생한다.")
        @Test
        void updateFail() {
            final String EXCEPTION_MESSAGE = "없는 게시글 입니다.";
            when(postRepository.findById(any(Long.class))).thenThrow(
                    new IllegalArgumentException(EXCEPTION_MESSAGE));

            assertThatThrownBy(() -> postService.updatePost(anyPostId, postUpdateRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(EXCEPTION_MESSAGE);
        }
    }

    @DisplayName("게시글을 삭제할 때")
    @Nested
    class delete {
        private Post post;

        @BeforeEach
        void setUp() {
            String title = "제목";
            String contents = "내용";
            post = new Post(title, contents);
        }

        @DisplayName("아이디에 해당하는 게시글이 있는 경우 게시글이 성공적으로 삭제된다.")
        @Test
        void deleteSuccess() {
            when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));

            Long anyPostId = 100L;
            PostDeleteResponse postDeleteResponse = postService.deletePost(anyPostId);
            assertThat(postDeleteResponse.isDeleted()).isTrue();
        }

        @DisplayName("아이디에 해당하는 게시글이 없는 경우 예외를 발생시킨다.")
        @Test
        void deleteFail() {
            final String EXCEPTION_MESSAGE = "없는 게시글 입니다.";
            when(postRepository.findById(any(Long.class))).thenThrow(
                    new IllegalArgumentException(EXCEPTION_MESSAGE));

            Long anyPostId = 100L;
            assertThatThrownBy(() -> postService.deletePost(anyPostId))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(EXCEPTION_MESSAGE);
        }
    }
}