package com.kalida.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kalida.dto.ExperienceDTO;
import com.kalida.dto.ExperienceNewDTO;
import com.kalida.model.Experience;
import com.kalida.model.Technology;
import com.kalida.model.User;
import com.kalida.service.ExperienceService;
import com.kalida.service.TechnologyService;
import com.kalida.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/experience")
public class ExperienceResource extends Controllable{
    
    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private UserService userService;

    @Autowired
    private TechnologyService technologyService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ExperienceDTO createExperience(@Valid @RequestBody ExperienceNewDTO experienceDTO){
        Technology technology = technologyService.findById(experienceDTO.getTechnology().getId());
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExperience(@PathVariable Long id, HttpServletRequest request){
        User userSaved = userService.findById(getUser().getId());
        userSaved.getExperiences()
            .removeIf(experienceFilter -> experienceFilter.getId().equals(id));
        userService.save(userSaved);
    }
}
