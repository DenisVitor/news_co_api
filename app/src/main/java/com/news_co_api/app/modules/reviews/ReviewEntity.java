package com.news_co_api.app.modules.reviews;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.news_co_api.app.modules.news.NewsEntity;
import com.news_co_api.app.modules.viewer.ViewerEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "reviews")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String review;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "viewer_posted")
    private ViewerEntity viewer_posted;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "news_related")
    private NewsEntity news_related;
}
