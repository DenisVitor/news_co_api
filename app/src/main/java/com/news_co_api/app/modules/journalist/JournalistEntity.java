package com.news_co_api.app.modules.journalist;

import java.util.List;
import java.util.UUID;

import com.news_co_api.app.context.Role;
import com.news_co_api.app.modules.news.NewsEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity(name = "journalists")
public class JournalistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String email;

    @Column(length = 1000)
    private String avatar;

    @OneToMany(mappedBy = "journalist_related", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<NewsEntity> news_related;
}
