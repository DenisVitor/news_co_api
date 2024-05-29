package com.news_co_api.modules.reviews;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.news_co_api.context.ReturnList;
import com.news_co_api.modules.viewer.ViewerEntity;
import com.news_co_api.modules.viewer.ViewerRepository;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    public ReviewService reviewServ;

    @Autowired
    public ViewerRepository viewerServ;

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

    @JsonView(ReturnList.ReviewReturn.class)
    @PatchMapping("/{id}")
    public ResponseEntity<?> returnPatchReview(@PathVariable UUID id, @RequestBody ReviewDTO payload) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User logged = (User) authentication.getPrincipal();
            ViewerEntity viewer = viewerServ.findByUsername(logged.getUsername()).orElseThrow();
            payload.setViewer_posted(viewer.getId());
        }
        return ResponseEntity.ok().body(reviewServ.updateReview(id, payload));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> returnDeleteReview(@PathVariable UUID id) throws NotFoundException {    
            reviewServ.deleteReview(id);
            return ResponseEntity.status(204).body(null);
    }
}
