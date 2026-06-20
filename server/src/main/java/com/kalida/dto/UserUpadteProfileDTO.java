package com.kalida.dto;

import java.io.Serializable;

import com.kalida.model.enums.TypeLang;
import com.kalida.validations.StringValidation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpadteProfileDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    @StringValidation(fieldName="username", min=1, max=100)
    private String username;

    @StringValidation(fieldName="nickname", min=1, max=100)
    private String nickname;

    @StringValidation(fieldName="name", min=1, max=140)
    private String name;

    @NotNull
    private TypeLang lang;

    @Email
    private String email;
}
