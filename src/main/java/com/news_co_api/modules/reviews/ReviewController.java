package com.news_co_api.modules.reviews;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news_co_api.modules.viewer.ViewerEntity;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    public ReviewService reviewServ;

    @PostMapping("/data")
    public ResponseEntity<?> returnByNewsAndUser(@RequestBody ReviewNewsUser payload) {
        return ResponseEntity.ok().body(reviewServ.getByIds(payload.getViewer_posted(), payload.getNews_related()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> returnReview(@PathVariable UUID id) {
        return ResponseEntity.ok().body(reviewServ.getReview(id));
    }

    @PostMapping
    public ResponseEntity<?> returnCreateEntity(@RequestBody ReviewDTO payload) {

        return ResponseEntity.status(201).body(reviewServ.createReview(payload));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> returnPatchReview(@PathVariable UUID id, @RequestBody ReviewDTO payload) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            ViewerEntity viewerEntity = (ViewerEntity) authentication.getPrincipal();
            payload.setViewer_posted(viewerEntity.getId());
        }
        return ResponseEntity.ok().body(reviewServ.updateReview(id, payload));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> returnDeleteReview(@PathVariable UUID id) {
        reviewServ.deleteReview(id);
        return ResponseEntity.status(204).body(null);
    }
}
