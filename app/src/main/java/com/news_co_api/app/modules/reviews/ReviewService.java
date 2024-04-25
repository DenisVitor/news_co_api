package com.news_co_api.app.modules.reviews;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private ModelMapper modMap;

    public List<ReviewEntity> getAll() {
        return reviewRepo.findAll();
    }

    public ReviewEntity getReview(UUID id) {
        return reviewRepo.findById(id).orElseThrow();
    }

    public ReviewEntity createReview(ReviewDTO payload) {
        return reviewRepo.save(modMap.map(payload, ReviewEntity.class));
    }

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
