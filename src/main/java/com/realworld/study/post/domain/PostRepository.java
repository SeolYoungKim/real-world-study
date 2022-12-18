package com.realworld.study.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

//TODO 이는 인터페이스만 있다. 해당 인터페이스가 어떻게 리포지토리로써 동작이 가능한가?
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
