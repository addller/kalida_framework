package com.kalida.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kalida.model.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long>{
    List<Experience> findByUserId(Long id);
}
