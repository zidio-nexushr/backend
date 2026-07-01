package com.zidio.nexus_hr.authservice.security;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlockService {

    private final Set<String>TokenKilling = ConcurrentHashMap.newKeySet();

    public void blockToken(String token){
        TokenKilling.add(token);
    }

    public boolean isBlockToken(String token){
        return TokenKilling.contains(token);
    }
}
