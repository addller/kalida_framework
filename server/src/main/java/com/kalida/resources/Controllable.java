package com.kalida.resources;
import com.kalida.model.User;
import com.kalida.service.UserService;

public abstract class Controllable{

    protected User getUser(){
        return UserService.autenthicated();
    }

}
