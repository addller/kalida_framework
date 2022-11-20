package com.kalida.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kalida.model.Technology;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Integer> {
    Technology findByName(String name);
}
