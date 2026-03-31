package com.kalida.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import io.jsonwebtoken.Claims;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDetailsService userDetailsService;

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, 
        JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        String header = request.getHeader("authorization");
        
        header = header == null? request.getHeader("Authorization"): header;
        try {
            if(header != null && header.startsWith("Bearer ")){
                UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
                if(auth != null){
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            chain.doFilter(request, response);
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(e.getLocalizedMessage());
        }
        
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            if(jwtTokenProvider.validateExpiration(token)){
                Claims claims = jwtTokenProvider.getClaims(token);
                String username = claims.getSubject();
                UserDetails user = userDetailsService.loadUserByUsername(username);
                return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());            
            }
            throw new InvalidJwtAuthenticationException("Expired JWToken");
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Invalid JWToken");
        }
    }
    
}
