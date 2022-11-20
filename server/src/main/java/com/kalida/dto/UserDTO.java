package com.kalida.dto;

import java.util.List;

import com.kalida.model.enums.TypeLang;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String nickName;

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
