package com.kalida.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalida.dto.TechnologyDTO;
import com.kalida.service.TechnologyService;

@RestController
@RequestMapping("/technology")
public class ControllerTecnology {

    @Autowired
    private TechnologyService technologyService;
    
    @GetMapping("/all")
    public List<TechnologyDTO> findAll(){
        return technologyService.findAll();
    }
}
