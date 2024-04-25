package com.news_co_api.app.modules.reviews;

import java.util.UUID;

public class ReviewDTO {
    private UUID id;

    private String review;

    private UUID viewer_posted;

    private UUID news_related;

    public UUID getId() {
        return id;
    }

    public UUID getNews_related() {
        return news_related;
    }

    public String getReview() {
        return review;
    }

    public UUID getViewer_posted() {
        return viewer_posted;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNews_related(UUID news_related) {
        this.news_related = news_related;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setViewer_posted(UUID viewer_posted) {
        this.viewer_posted = viewer_posted;
    }
}
