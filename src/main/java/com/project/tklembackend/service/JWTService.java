package com.project.tklembackend.service;

import com.project.tklembackend.model.Roles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
@Service
public class JWTService {

    @Value("${jwt.key}")
    private String jwtKey;

    public String generateToken(String email, Roles role){

        long expirationDate = (86_400_000L)/*1 day*/ * 364;
        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setExpiration(new Date(System.currentTimeMillis() + expirationDate))
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .compact();
    };

    public String extractJWT(String token){
        return Jwts.parser()
                .setSigningKey(jwtKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    };

}
