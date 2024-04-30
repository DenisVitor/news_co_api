package com.news_co_api.app.modules.reviews;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.news_co_api.app.modules.news.NewsEntity;
import com.news_co_api.app.modules.news.NewsRepository;
import com.news_co_api.app.modules.viewer.ViewerEntity;
import com.news_co_api.app.modules.viewer.ViewerRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private ModelMapper modMap;

    @Autowired
    private ViewerRepository viewerRepo;

    @Autowired
    private NewsRepository newsRepo;

    public List<ReviewEntity> getAll() {
        return reviewRepo.findAll();
    }

    public ReviewEntity getReview(UUID id) {
        return reviewRepo.findById(id).orElseThrow();
    }

    @Transactional
    public ReviewEntity createReview(ReviewDTO payload) {
        ReviewEntity review = modMap.map(payload, ReviewEntity.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            ViewerEntity viewer = viewerRepo.findByUsername(username).orElseThrow();
            review.setViewer_posted(viewer);
        }
        NewsEntity news = newsRepo.findById(payload.getNews_related()).orElseThrow();
        review.setNews_related(news);
        return reviewRepo.save(review);
    }

    @SuppressWarnings("null")
    public ReviewEntity updateReview(UUID id, ReviewDTO payload) {
        ReviewEntity reviewToUpdate = reviewRepo.findById(id).orElseThrow();
        if (reviewToUpdate != null) {
            if (payload.getReview() != null) {
                reviewToUpdate.setReview(payload.getReview());
            }
        }
        return reviewRepo.save(reviewToUpdate);
    }

    public void deleteReview(UUID id) {
        if (reviewRepo.existsById(id)) {
            reviewRepo.deleteById(id);
        }
    }
}
