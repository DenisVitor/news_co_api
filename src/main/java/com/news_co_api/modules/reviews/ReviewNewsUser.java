package com.news_co_api.modules.reviews;

import java.util.UUID;

public class ReviewNewsUser {
    private UUID news_related;
    private UUID viewer_posted;

    public UUID getNews_related() {
        return news_related;
    }

    public UUID getViewer_posted() {
        return viewer_posted;
    }

    public void setNews_related(UUID news_related) {
        this.news_related = news_related;
    }

    public void setViewer_posted(UUID viewer_posted) {
        this.viewer_posted = viewer_posted;
    }
}
