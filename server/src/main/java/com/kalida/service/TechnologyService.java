package com.kalida.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kalida.dto.TechnologyDTO;
import com.kalida.exception.DomainException;
import com.kalida.model.Technology;
import com.kalida.repository.TechnologyRepository;

@Service
public class TechnologyService {

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TechnologyDTO> findAll() {
        return technologyRepository.findAll()
            .stream()
            .map(technology -> modelMapper.map(technology, TechnologyDTO.class))
            .collect(Collectors.toList());
    }

    public Technology findById(Integer id) {
        return technologyRepository.findById(id)
            .orElseThrow(() -> new DomainException("Technology not found by id: "+id, HttpStatus.NOT_FOUND));
    }
    
}
