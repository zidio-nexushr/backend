package com.zidio.nexus_hr.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    //get the jwt secret key from the environment varriable
    @Value("${app.jwt.secret}")
    private String secretKey;

    //get the expiration time from the environment varriable
    @Value("${app.jwt.expiration}")
    private long expiration;

    //encode secrete key
    private SecretKey getSigningKey() {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return key;
    }


    //generate token
    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    //get the payload form the token
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //get the role from the payload
    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    //check if the token is valid
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    //extract the email from the token
    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }
}
