package com.kalida.model.enums;

import com.kalida.exception.DomainException;

import lombok.Getter;

@Getter
public enum TypeTechnology {
    
    PROGRAM_LANGUAGE(1),
    FRAMEWORK(2),
    SGBD(3),
    OTHER(4);

    private short cod;

    private TypeTechnology(int cod){
        this.cod = (short) cod;
    }

    public static TypeTechnology toEnum(int cod){
        for(TypeTechnology technology: TypeTechnology.values()){
            if(technology.cod == cod) return technology;
        }
        throw new DomainException("TypeTechnology cod not found: "+cod);
    }
}
