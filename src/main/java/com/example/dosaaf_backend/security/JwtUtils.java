package com.example.dosaaf_backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${dosaaf_backend.app.secret}")
    private String jwtSecret;
    @Value("${dosaaf_backend.app.expirationMs}")
    private int jwtExperationMs;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().subject((userPrincipal.getEmail())).issuedAt(new Date()).expiration(new Date(new Date().getTime() + jwtExperationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public boolean validateJwtToken(String jwt){
        try {
            Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(jwt);
            return true;
        }
        catch (MalformedJwtException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public String getEmailFromJwtToken(String jwt){
        return Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(jwt).getBody().getSubject();
    }
}
