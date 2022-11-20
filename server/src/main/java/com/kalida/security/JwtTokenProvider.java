package com.kalida.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenProvider {

    @Autowired
    private UserDetailsService userDetailService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.expire-after}")
    private long validityInMilisseconds;

    public String createToken(User user) {
        Claims claims = Jwts.claims()
            .setSubject(user.getUsername());

        claims.put("roles", user.getRoles());
        claims.put("userId_", user.getId());
        claims.put("nick_name_", user.getNickName());
        claims.put("lang_", user.getLang().getInitials());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilisseconds);
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Date expiration = getClaims(token).getExpiration();
            Date now = new Date();
            return now.before(expiration);
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid token");
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public Claims getClaims(String token){
        return Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }


    @PostConstruct
    public void init() {
        secret = Base64.getEncoder().encodeToString(("a$gN#Yj*LZ"+secret).getBytes());
    }
}