package com.news_co_api.modules.reviews;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.news_co_api.modules.news.NewsEntity;
import com.news_co_api.modules.news.NewsRepository;
import com.news_co_api.modules.viewer.ViewerEntity;
import com.news_co_api.modules.viewer.ViewerRepository;

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

    public UUID getByIds(UUID viewerId, UUID newsId) {
        return reviewRepo.findByViewerAndByNews(viewerId, newsId).orElseThrow();
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

    public ReviewEntity updateReview(UUID id, ReviewDTO payload) {
        ReviewEntity reviewToUpdate = reviewRepo.findById(id).orElseThrow();
        if (reviewToUpdate != null) {
            if (payload.getReview() != null) {
                reviewToUpdate.setReview(payload.getReview());
            }
            return reviewRepo.save(reviewToUpdate);
        }
        return reviewToUpdate;
    }

    public void deleteReview(UUID id) throws NotFoundException {
        if (reviewRepo.existsById(id) == false) {
            throw new NotFoundException();
        }
        reviewRepo.deleteById(id);
    }
}
