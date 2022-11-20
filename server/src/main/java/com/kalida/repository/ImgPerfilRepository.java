package com.kalida.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kalida.model.ImgPerfil;

@Repository
public interface ImgPerfilRepository extends JpaRepository<ImgPerfil, Long>{
    
    ImgPerfil findByUserId(long id);
}
