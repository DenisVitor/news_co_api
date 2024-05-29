package com.news_co_api.modules.news;

import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepo;

    @Autowired
    private ModelMapper modMap;

    @Transactional
    public List<NewsEntity> getAllNews() {
        return newsRepo.findAll();
    }

    @Transactional
    public NewsEntity getNews(UUID id) {
        NewsEntity news = newsRepo.findById(id).orElseThrow();
        Hibernate.initialize(news.getReviews_related());
        return news;
    }

    @Transactional
    public NewsEntity createNews(NewsDTO payload) {
        return newsRepo.save(modMap.map(payload, NewsEntity.class));
    }

    @SuppressWarnings("null")
    public NewsEntity updateNews(UUID id, NewsDTO payload) {
        NewsEntity newsToUpdate = newsRepo.findById(id).orElseThrow();
        if (newsToUpdate != null) {
            if (payload.getTitle() != null) {
                newsToUpdate.setTitle(payload.getTitle());
            }
            if (payload.getSubtitle() != null) {
                newsToUpdate.setSubtitle(payload.getSubtitle());
            }
            if (payload.getContent() != null) {
                newsToUpdate.setContent(payload.getContent());
            }
            if (payload.getType() != null) {
                newsToUpdate.setType(payload.getType());
            }
        }
        return newsRepo.save(newsToUpdate);
    }

    public void deleteNews(UUID id) {
        if (newsRepo.existsById(id)) {
            newsRepo.deleteById(id);
        }
    }
}
