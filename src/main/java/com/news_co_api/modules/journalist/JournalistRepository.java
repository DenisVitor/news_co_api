package com.news_co_api.modules.journalist;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalistRepository extends JpaRepository<JournalistEntity, UUID> {
    
}
