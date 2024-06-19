package com.news_co_api.modules.news;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsServ;

    @JsonView(ReturnList.NewsList.class)
    @CrossOrigin(origins = "https://news-co-alpha.vercel.app")
    @GetMapping
    public ResponseEntity<?> returnAll() {
        return ResponseEntity.ok().body(newsServ.getAllNews());
    }

    @CrossOrigin(origins = "https://news-co-alpha.vercel.app")
    @GetMapping("/{id}")
    public ResponseEntity<?> returnNews(@PathVariable UUID id) {
        return ResponseEntity.ok().body(newsServ.getNews(id));
    }

    @CrossOrigin(origins = "https://news-co-alpha.vercel.app")
    @PostMapping
    public ResponseEntity<?> returnCreateNews(@RequestBody NewsDTO payload) {
        return ResponseEntity.status(201).body(newsServ.createNews(payload));
    }

    @CrossOrigin(origins = "https://news-co-alpha.vercel.app")
    @PatchMapping("/{id}")
    public ResponseEntity<?> returnUpdateNews(@PathVariable UUID id, @RequestBody NewsDTO payload) {
        return ResponseEntity.ok().body(newsServ.updateNews(id, payload));
    }

    @CrossOrigin(origins = "https://news-co-alpha.vercel.app")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> returnDeleteNews(@PathVariable UUID id) {
        newsServ.deleteNews(id);
        return ResponseEntity.status(204).body(null);
    }
}
