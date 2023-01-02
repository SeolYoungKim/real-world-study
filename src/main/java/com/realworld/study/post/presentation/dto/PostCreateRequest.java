package com.realworld.study.post.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)  //TODO 요청 DTO에 기본 생성자가 필요한 이유와 접근제어자 Private가 가능한 이유는 무엇인가?
public class PostCreateRequest {  // @RequestBody : Json -> DTO "objectMapper" reflection jvm
    private String title;
    private String contents;

    public PostCreateRequest(final String title, final String contents) {
        this.title = title;
        this.contents = contents;
    }
}
