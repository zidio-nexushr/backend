package com.zidio.nexus_hr.authservice.security;

import com.zidio.nexus_hr.authservice.Enum.Permission;
import com.zidio.nexus_hr.authservice.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
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
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        Set<Permission> permissions = RoleBasePermission.getRolePermission().get(user.getRole());
        claims.put("permissions", permissions.stream()
                .map(Permission::name)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
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
