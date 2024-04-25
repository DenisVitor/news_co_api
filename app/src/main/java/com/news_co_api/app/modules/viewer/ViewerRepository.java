package com.news_co_api.app.modules.viewer;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewerRepository extends JpaRepository<ViewerEntity, UUID> {
    Optional<ViewerEntity> findByUsername(String username);
}
