package com.news_co_api.modules.viewer;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ViewerService {
    @Autowired
    private ViewerRepository viewerRepo;

    @Autowired
    private PasswordEncoder encoder;

    public UUID getByToken(String username) throws Exception {
        return viewerRepo.findByUsername(username).orElseThrow().getId();
    }

    public List<ViewerEntity> getAllViewers() {
        return viewerRepo.findAll();
    }

    public ViewerEntity getViewer(UUID id) {
        return viewerRepo.findById(id).orElseThrow();
    }

    @SuppressWarnings("null")
    public ViewerEntity updateViewer(UUID id, ViewerDTO payload) {
        ViewerEntity viewerToUpdate = viewerRepo.findById(id).orElseThrow();
        if (viewerToUpdate != null) {
            if (payload.getEmail() != null) {
                viewerToUpdate.setEmail(payload.getEmail());
            }
            if (payload.getAvatar() != null) {
                viewerToUpdate.setAvatar(payload.getAvatar());
            }
            if (payload.getUsername() != null) {
                viewerToUpdate.setUsername(payload.getUsername());
            }
            if (payload.getPassword() != null) {
                viewerToUpdate.setPassword(encoder.encode(payload.getPassword()));
            }
        }
        return viewerRepo.save(viewerToUpdate);
    }

    public void deleteViewer(UUID id) {
        if (viewerRepo.existsById(id)) {
            viewerRepo.deleteById(id);
        }
    }
}
