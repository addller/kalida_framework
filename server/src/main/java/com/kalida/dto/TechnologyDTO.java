package com.kalida.dto;

import com.kalida.model.enums.TypeTechnology;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TechnologyDTO {

    private Integer id;

    private String name;

    private String descritpion;

    private short typeTechnology;

    public TechnologyDTO(String name, TypeTechnology typeTechnology){
        this.name = name;
        this.typeTechnology = typeTechnology.getCod();
    }

    public TypeTechnology getTypeTechnology(){
        return TypeTechnology.toEnum(typeTechnology);
    }

    public void setTypeTechnology(TypeTechnology typeTechnology){
        this.typeTechnology = typeTechnology.getCod();
    }

}
