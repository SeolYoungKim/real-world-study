package com.realworld.study.post.application;

import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.repository.MemberRepository;
import com.realworld.study.post.domain.Post;
import com.realworld.study.post.domain.PostBody;
import com.realworld.study.post.domain.PostTitle;
import com.realworld.study.post.domain.repository.PostRepository;
import com.realworld.study.post.dto.request.PostRequest;
import com.realworld.study.post.dto.response.PostUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public PostUrlResponse create(PostRequest postRequest) {
        Post post = createPost(postRequest);
        Post savedPost = postRepository.save(post);
        return new PostUrlResponse(savedPost.getId());
    }

    private Post createPost(PostRequest postRequest) {
         return createPost(postRequest.getTitle(), postRequest.getBody());
    }

    private Post createPost(String title, String body) {
        Member author = findUserById();
        return new Post(
            author,
            new PostTitle(title),
            new PostBody(body),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }

    private Member findUserById() {
        return memberRepository.findById(1L).orElseThrow();
    }
}