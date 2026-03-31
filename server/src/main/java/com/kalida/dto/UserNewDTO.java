package com.kalida.dto;

import com.kalida.model.enums.TypeLang;
import com.kalida.validations.PasswordValidation;
import com.kalida.validations.StringValidation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserNewDTO {

    @StringValidation(fieldName="username", min=1, max=100)
    private String username;

    @StringValidation(fieldName="nickname", min=1, max=100)
    private String nickname;

    @StringValidation(fieldName="name", min=1, max=140)
    private String name;

    @PasswordValidation(min=8, max=200)
    private String password;

    @NotNull    
    private TypeLang lang;

    @Email
    private String email;

    public UserNewDTO(String username, String nickname, String name, String password, TypeLang lang, String email){
        this.username = username;
        this.nickname = nickname;
        this.name = name;
        this.password = password;
        this.lang = lang;
        this.email = email;
    }
}
