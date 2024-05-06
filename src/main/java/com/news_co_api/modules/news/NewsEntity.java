package com.news_co_api.modules.news;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.news_co_api.context.ReturnList;
import com.news_co_api.context.Role;
import com.news_co_api.modules.journalist.JournalistEntity;
import com.news_co_api.modules.reviews.ReviewEntity;

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
    @JsonView(ReturnList.NewsList.class)
    private UUID id;

    @JsonView(ReturnList.NewsList.class)
    @Column(nullable = false)
    private String title;
    
    @JsonView(ReturnList.NewsList.class)
    @Column(nullable = false)
    private String subtitle;

    @JsonView(ReturnList.NewsList.class)
    @Column(nullable = true)
    private String image;
    
    @JsonView(ReturnList.NewsList.class)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role type;

    @Column(nullable = false, length = 2000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "journalist_related")
    @JsonIgnoreProperties({ "news_related" })
    private JournalistEntity journalist_related;

    @JsonIgnoreProperties({ "news_related" })
    @OneToMany(mappedBy = "news_related", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ReviewEntity> reviews_related;
}
