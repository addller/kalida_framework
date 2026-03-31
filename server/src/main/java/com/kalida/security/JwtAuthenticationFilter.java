package com.kalida.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kalida.exception.StandardError;
import com.kalida.model.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

//aula 71, tem que implementar o AutheticationFailureHandler direto do github pra mudar o código de erro para 401
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
            StandardError error = new StandardError(exception, request.getRequestURI(), HttpStatusCode.valueOf(401), "Email or password inválid");
            response.getWriter().append(new ObjectMapper().writeValueAsString(error));
        }

    }
}
