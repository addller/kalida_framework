package com.kalida.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kalida.model.enums.TypeTechnology;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Technology implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String descritpion;

    @Column(name = "type_technology")
    private short typeTechnology;

    @JsonBackReference
    @OneToMany(mappedBy = "technology", orphanRemoval=true, fetch = FetchType.LAZY)
    private List<Experience> experiences;

    public TypeTechnology getTypeTechnology(){
        return TypeTechnology.toEnum(typeTechnology);
    }

    public void setTypeTechnology(TypeTechnology typeTechnology){
        this.typeTechnology = typeTechnology.getCod();
    }

}
