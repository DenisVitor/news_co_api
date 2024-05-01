package com.news_co_api.modules.viewer;

import java.util.UUID;

public class ViewerDTO {
    private UUID id;

    private String username;

    private String email;

    private String password;

    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public UUID getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
