package com.realworld.study.learning_test;

import com.realworld.study.auth.FakeAuthentication;
import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import com.realworld.study.post.application.PostService;
import com.realworld.study.post.domain.PostRepository;
import com.realworld.study.post.presentation.dto.PostCreateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionSynchronizationManager;

//@Transactional
@SpringBootTest
public class TxTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostService postService;

    @BeforeEach
    void setUp() {
        memberRepository.save(Member.builder()
                .memberName("kim")
                .email("email@domain.com")
                .password("12345678")
                .build());
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("트랜잭션 동기화 매니저는 스레드 로컬로 커넥션을 관리한다고 한다. 그럼 커넥션이 생성될 때 마다 스레드가 할당되는가? 에서 시작된 궁금증을 해결하는 테스트")
    @Test
    void txThreadTest() {
        System.out.println(TransactionSynchronizationManager.getResourceMap());

        postService.createPost(new PostCreateRequest("title", "content"), new FakeAuthentication());
        postService.createPost(new PostCreateRequest("title", "content"), new FakeAuthentication());
        postService.createPost(new PostCreateRequest("title", "content"), new FakeAuthentication());
        postService.createPost(new PostCreateRequest("title", "content"), new FakeAuthentication());
    }
}
