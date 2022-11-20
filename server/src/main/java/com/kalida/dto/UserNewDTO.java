package com.kalida.dto;

import com.kalida.model.enums.TypeLang;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserNewDTO {

    private String username;

    private String nickName;

    private String name;

    private String password;

    private short lang;

    private String email;

    public UserNewDTO(String username, String nickName, String name, String password, short lang, String email){
        this.username = username;
        this.nickName = nickName;
        this.name = name;
        this.password = password;
        this.lang = lang;
        this.email = email;
    }

    public TypeLang getLang() {
        return TypeLang.toEnum(this.lang);
    }

    public void setLang(TypeLang typeLang) {
        this.lang = typeLang.getCod();
    }
}
