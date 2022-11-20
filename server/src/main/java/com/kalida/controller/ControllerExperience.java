package com.kalida.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalida.dto.ExperienceDTO;
import com.kalida.dto.TechnologyDTO;
import com.kalida.model.Experience;
import com.kalida.model.Technology;
import com.kalida.security.User;
import com.kalida.security.UserService;
import com.kalida.service.ExperienceService;
import com.kalida.service.TechnologyService;

@RestController
@RequestMapping("/experience")
public class ControllerExperience extends Controllable{
    
    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private UserService userService;

    @Autowired
    private TechnologyService technologyService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ExperienceDTO createExperience(@RequestBody ExperienceDTO experienceDTO){
        Technology technology = technologyService.findById(experienceDTO.getTechnology().getId());
        experienceDTO.setTechnology(modelMapper.map(technology, TechnologyDTO.class));

        Experience experience = modelMapper.map(experienceDTO, Experience.class);
        experience.setTechnology(technology);
        experience.setUser(userService.findById(getUser().getId()));
        experience = experienceService.save(experience);
        
        return modelMapper.map(experience, ExperienceDTO.class);
    }

    @GetMapping
    public List<ExperienceDTO> findExperiences(){
        User user = getUser();
        return experienceService
            .findByUserId(user.getId())
            .stream()
            .map(experience -> modelMapper.map(experience, ExperienceDTO.class))
            .collect(Collectors.toList());
    }
}
