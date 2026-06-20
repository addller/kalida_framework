package com.kalida.security;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kalida.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {
    
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private String pepper;

    @Value("${jwt.token.expire-after-seconds}")
    private long expirationInSecond;

    private Key key;
    
    public String createToken(User user){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusSeconds(expirationInSecond);

        return Jwts.builder()
            .claim("roles", user.getRoles())
            .claim("userId", user.getId())
            .claim("nickname", user.getNickname())
            .claim("sub", user.getUsername())
            .claim("lang_", user.getLang().name())
            //.claim("iat", now.toString())
            .claim("exp_", expiration.toString())
            .signWith(key)
            .compact();
    }

    public Claims getClaims(String token){
        return Jwts.parser()
            .verifyWith((SecretKey) key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    // get username from JWT token
    public String getUsername(String token){
        return getClaims(token).get("sub", String.class);
    }

    // validate JWT token
    public boolean validateExpiration(String token){
        String rawDate = getClaims(token)
            .get("exp_", String.class);

        LocalDateTime expiration = LocalDateTime.parse(rawDate);
        return expiration.isAfter(LocalDateTime.now());
    }

    @PostConstruct
    public void init() {
        secret = Base64.getEncoder().encodeToString((pepper+secret).getBytes());
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String extractToken(HttpServletRequest request){
        String header = request.getHeader("authorization");
        header = header == null? request.getHeader("Authorization"): header;
        if(header != null && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }

}