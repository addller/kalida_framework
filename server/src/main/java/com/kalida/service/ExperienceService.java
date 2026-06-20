package com.kalida.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kalida.exception.DomainException;
import com.kalida.model.Experience;
import com.kalida.repository.ExperienceRepository;

@Service
public class ExperienceService {
    
    @Autowired
    private ExperienceRepository experienceRepository;

    public Experience findById(Long id){
        return experienceRepository.findById(id)
            .orElseThrow(() -> new DomainException("Experience  not found", HttpStatus.NOT_FOUND));
    }

    public List<Experience> findByUserId(Long id) {
        return experienceRepository.findByUserId(id);
    }

    public Experience save(Experience experience){
        return experienceRepository.save(experience);
    }


}
