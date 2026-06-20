package com.kalida.dto;

import java.io.Serializable;
import java.util.List;

import com.kalida.model.enums.TypeLang;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String nickname;

    private String name;

    private short lang;

    private String email;

    private List<ExperienceDTO> experiences;

    public TypeLang getLang() {
        return TypeLang.toEnum(this.lang);
    }

    public void setLang(TypeLang typeLang) {
        this.lang = typeLang.getCod();
    }
}
