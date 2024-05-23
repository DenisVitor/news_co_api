package com.news_co_api.modules.reviews;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
    @Query("select r from reviews r where r.viewer_posted.id = ?1 and r.news_related.id = ?2")
    Optional<ReviewEntity> findByViewerAndByNews(UUID viewerId, UUID newsId);
}

