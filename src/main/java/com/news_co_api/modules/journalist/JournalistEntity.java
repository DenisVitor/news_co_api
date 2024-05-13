package com.news_co_api.modules.journalist;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.news_co_api.context.ReturnList;
import com.news_co_api.context.Role;
import com.news_co_api.modules.news.NewsEntity;

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
    @JsonView(ReturnList.JournalistList.class)
    private UUID id;

    @JsonView(ReturnList.JournalistList.class)
    @Column(nullable = false)
    private String name;

    @JsonView(ReturnList.JournalistList.class)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @JsonView(ReturnList.JournalistList.class)
    @Column(nullable = false)
    private String email;

    @JsonView(ReturnList.JournalistList.class)
    @Column(length = 1000)
    private String avatar;

    @JsonIgnoreProperties({ "journalist_related", "reviews_related", "content" })
    @OneToMany(mappedBy = "journalist_related", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<NewsEntity> news_related;
}
