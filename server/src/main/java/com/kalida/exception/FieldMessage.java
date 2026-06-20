package com.kalida.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldMessage implements Serializable{

    private String name;
    private String message;

}