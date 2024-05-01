package com.news_co_api.modules.news;

import java.util.UUID;

import com.news_co_api.context.Role;

public class NewsDTO {
    private UUID id;

    private String title;

    private Role type;

    private String content;

    private String subtitle;

    private UUID journalist_related;

    public String getContent() {
        return content;
    }

    public UUID getId() {
        return id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTitle() {
        return title;
    }

    public Role getType() {
        return type;
    }

    public UUID getJournalist_related() {
        return journalist_related;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(Role type) {
        this.type = type;
    }

    public void setJournalist_related(UUID journalist_related) {
        this.journalist_related = journalist_related;
    }
}
