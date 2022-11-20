package com.kalida.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.kalida.security.JwtTokenProvider;
import com.kalida.security.User;
import com.kalida.security.UserService;

import net.minidev.json.JSONObject;

public abstract class Controllable{

    @Autowired
    private JwtTokenProvider tokenProvider;

    public JwtTokenProvider getTokenProvider() {
        return tokenProvider;
    }

    protected User getUser(){
        return UserService.autenthicated();
    }

    protected ResponseEntity<Void> noContent(){
        return ResponseEntity.noContent().build();
    }

    public final long toLong(JSONObject data, String key) {
        return data.getAsNumber(key).longValue();
    }
    public final int toInt(JSONObject data, String key) {
        return data.getAsNumber(key).intValue();
    }
    public final short toShort(JSONObject data, String key) {
        return data.getAsNumber(key).shortValue();
    }
}
