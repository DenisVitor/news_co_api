package com.news_co_api.modules.journalist;

import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalistService {
    @Autowired
    private JournalistRepository journoRepo;

    @Autowired
    private ModelMapper modMap;

    public List<JournalistEntity> getAllJournos() {
        return journoRepo.findAll();
    }

    @Transactional
    public JournalistEntity getJourno(UUID id) {
        JournalistEntity journo = journoRepo.findById(id).orElseThrow();
        Hibernate.initialize(journo.getNews_related());
        return journo;
    }

    public JournalistEntity createJourno(JournalistDTO payload) {
        return journoRepo.save(modMap.map(payload, JournalistEntity.class));
    }

    @SuppressWarnings("null")
    public JournalistEntity updateJourno(UUID id, JournalistDTO payload) {
        JournalistEntity journoToUpdate = journoRepo.findById(id).orElseThrow();
        if (journoToUpdate != null) {
            if (payload.getName() != null) {
                journoToUpdate.setName(payload.getName());
            }
            if (payload.getRole() != null) {
                journoToUpdate.setRole(payload.getRole());
            }
            if (payload.getAvatar() != null) {
                journoToUpdate.setAvatar(payload.getAvatar());
            }
            if (payload.getEmail() != null) {
                journoToUpdate.setEmail(payload.getEmail());
            }
        }
        return journoRepo.save(journoToUpdate);
    }

    public void removeJourno(UUID id) {
        if (journoRepo.existsById(id)) {
            journoRepo.deleteById(id);
        }
    }
}
