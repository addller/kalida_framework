package com.kalida.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kalida.model.ImgPerfil;
import com.kalida.repository.ImgPerfilRepository;

@Service
public class ImgPerfilService {
    
    @Autowired
    private ImgPerfilRepository imgPerfilRepository;

    public ImgPerfil findByUserId(Long userId){
        return imgPerfilRepository.findByUserId(userId);
    }

    public ImgPerfil save(ImgPerfil imgPerfil) {
        return imgPerfilRepository.save(imgPerfil);
    }
}
