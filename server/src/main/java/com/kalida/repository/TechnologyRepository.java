package com.kalida.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kalida.model.Technology;

public interface TechnologyRepository extends JpaRepository<Technology, Integer> {
    Technology findByName(String name);
}
