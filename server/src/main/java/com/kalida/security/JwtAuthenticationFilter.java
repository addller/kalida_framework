package com.kalida.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.minidev.json.JSONObject;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
 
    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            AccountCredentials credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), AccountCredentials.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), new ArrayList<>());
            Authentication auth = authenticationManager.authenticate(authToken);
            return auth;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        User user = ((User) auth.getPrincipal());
        String token = jwtTokenProvider.createToken(user);
        response.addHeader("Authorization", "Bearer "+token);
        response.addHeader("access-control-expose-headers","Authorization");
    }

    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler{

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {
            
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json(){
            JSONObject json = new JSONObject();
            json.put("timestamp", new Date().getTime());
            json.put("status", 401);
            json.put("error", "unauthorized");
            json.put("message", "Email or password inválid");
            json.put("path", "/login");
            return json.toJSONString();
        }

    }
}
