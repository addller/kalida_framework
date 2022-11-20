package com.kalida.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalida.model.Experience;
import com.kalida.repository.ExperienceRepository;

@Service
public class ExperienceService {
    
    @Autowired
    private ExperienceRepository experienceRepository;

    public Collection<Experience> findByUserId(Long id) {
        return experienceRepository.findByUserId(id);
    }

    public Experience save(Experience experience){
        return experienceRepository.save(experience);
    }


}
