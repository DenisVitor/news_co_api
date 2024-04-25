package com.news_co_api.app.modules.viewer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/viewers")
public class ViewerController {
    @Autowired
    private ViewerService viewerServ;

    @GetMapping
    public ResponseEntity<?> returnAllViewers() {
        return ResponseEntity.ok().body(viewerServ.getAllViewers());
    }
}
