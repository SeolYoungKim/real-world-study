package com.realworld.study.article.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleFavoriteRepository extends JpaRepository<ArticleFavorite, Long> {
    boolean existsByArticle_IdAndUser_Id(Long articleId, Long userId);
    Optional<ArticleFavorite> findArticleFavoriteByArticle_IdAndUser_Id(Long articleId, Long userId);
}
