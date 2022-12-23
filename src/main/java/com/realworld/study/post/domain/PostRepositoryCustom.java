package com.realworld.study.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> pagedPosts(Pageable pageable);
}
