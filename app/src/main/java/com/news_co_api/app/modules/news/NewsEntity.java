package com.news_co_api.app.modules.news;

import java.util.List;
import java.util.UUID;

import com.news_co_api.app.context.Role;
import com.news_co_api.app.modules.journalist.JournalistEntity;
import com.news_co_api.app.modules.reviews.ReviewEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity(name = "news")
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role type;

    @Column(nullable = false, length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journalist_related")
    private JournalistEntity journalist_related;

    @OneToMany(mappedBy = "news_related", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ReviewEntity> reviews_related;
}
