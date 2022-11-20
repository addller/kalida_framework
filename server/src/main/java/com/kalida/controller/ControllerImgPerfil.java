package com.kalida.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kalida.model.ImgPerfil;
import com.kalida.security.User;
import com.kalida.security.UserService;
import com.kalida.service.ImgPerfilService;

@RestController
@RequestMapping("/img_perfil")
public class ControllerImgPerfil extends Controllable{

    @Autowired
    private UserService userService;

    @Autowired
    private ImgPerfilService imgPerfilService;

    @GetMapping("/{userId}")
    public String getImgPerfil(@PathVariable Long userId){
        ImgPerfil imgPerfil = imgPerfilService.findByUserId(userId);
        return imgPerfil != null? imgPerfil.toBase64() : null;
    }
    
    @PutMapping
    public String updateImgPerfil(@RequestParam("imgPerfil") MultipartFile file) throws IOException{
        User user = userService.findById(getUser().getId());
        String fileName = file.getResource().getFilename();
        String extension = fileName.split("\\.")[1];

        ImgPerfil imgPerfil = imgPerfilService.findByUserId(user.getId());
        if(imgPerfil == null) imgPerfil = new ImgPerfil();
        imgPerfil.setInfo(file.getBytes(), user, extension, System.currentTimeMillis());
        
        user.setImgPerfil(imgPerfil);
        imgPerfil.setUser(user);
        imgPerfilService.save(imgPerfil);
        return imgPerfil.toBase64();
    }
}
