package com.kalida.dto;

import java.io.Serializable;

import com.kalida.model.enums.TypeLang;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String nickname;

    private String name;

    private TypeLang lang;

    private String email;
}
