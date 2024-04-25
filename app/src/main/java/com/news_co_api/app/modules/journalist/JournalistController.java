package com.news_co_api.app.modules.journalist;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journalists")
public class JournalistController {
    @Autowired
    private JournalistService journoService;

    @GetMapping
    public ResponseEntity<?> returnAll() {
        return ResponseEntity.ok().body(journoService.getAllJournos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> returnJourno(@PathVariable UUID id) {
        return ResponseEntity.ok().body(journoService.getJourno(id));
    }

    @PostMapping
    public ResponseEntity<?> returnCreateJourno(@RequestBody JournalistDTO payload) {
        return ResponseEntity.status(201).body(journoService.createJourno(payload));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> returnUpdateJourno(@PathVariable UUID id, @RequestBody JournalistDTO payload) {
        return ResponseEntity.ok().body(journoService.updateJourno(id, payload));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> returnDeleteJourno(@PathVariable UUID id) {
        journoService.removeJourno(id);
        return ResponseEntity.status(204).body(null);
    }
}
