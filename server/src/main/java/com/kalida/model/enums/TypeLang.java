package com.kalida.model.enums;

import org.springframework.http.HttpStatus;

import com.kalida.exception.DomainException;

import lombok.Getter;

@Getter
public enum TypeLang {

    PT_BR(1, "Portugues-BR", "pt-BR"),
    EN_US(2, "English-US", "en-US");
    
    private short cod;
    private String description;
    private String initials;

    private TypeLang(int cod, String descrition, String initials){
        this.cod = (short) cod;
        this.description = descrition;
        this.initials = initials;
    }

    public static TypeLang toEnum(int cod){
        for(TypeLang lang : TypeLang.values()){
            if(lang.cod == cod) return lang;
        }
        throw new DomainException("TypeLang cod not found: "+cod, HttpStatus.BAD_REQUEST);
    }
}
