package com.news_co_api.modules.viewer;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/viewers")
public class ViewerController {
    @Autowired
    private ViewerService viewerServ;

    @CrossOrigin(origins = "*")
    @GetMapping("/token")
    public ResponseEntity<?> returnViewer() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User viewer = (User) authentication.getPrincipal();
            String viewerName = viewer.getUsername();
            return ResponseEntity.ok().body(viewerServ.getByToken(viewerName));
        }
        var errorMsg = new HashMap<String, String>();
        errorMsg.put("error", "viewer not logged");
        return ResponseEntity.status(401).body(errorMsg);
    }

    @CrossOrigin(origins = "*")
    @PatchMapping
    public ResponseEntity<?> returnUpdateViewer(@RequestBody ViewerDTO payload) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            ViewerEntity viewer = (ViewerEntity) authentication.getPrincipal();
            UUID viewerId = viewer.getId();
            return ResponseEntity.ok().body(viewerServ.updateViewer(viewerId, payload));
        }
        var errorMsg = new HashMap<String, String>();
        errorMsg.put("error", "viewer not logged");
        return ResponseEntity.status(401).body(errorMsg);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping
    public ResponseEntity<?> returnDeleteViewer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            ViewerEntity viewer = (ViewerEntity) authentication.getPrincipal();
            UUID viewerId = viewer.getId();
            viewerServ.deleteViewer(viewerId);
            return ResponseEntity.status(204).body(null);
        }
        var errorMsg = new HashMap<String, String>();
        errorMsg.put("error", "viewer not logged");
        return ResponseEntity.status(401).body(errorMsg);
    }
}
