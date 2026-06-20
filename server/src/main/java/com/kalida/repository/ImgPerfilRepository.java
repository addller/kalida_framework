package com.kalida.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kalida.model.ImgPerfil;

public interface ImgPerfilRepository extends JpaRepository<ImgPerfil, Long>{
    
    ImgPerfil findByUserId(long id);
}
