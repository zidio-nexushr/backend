package com.zidio.nexus_hr.authservice.controller;

import com.zidio.nexus_hr.authservice.dto.RegisterRequest;
import com.zidio.nexus_hr.authservice.dto.RegisterResponse;
import com.zidio.nexus_hr.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {

        System.out.println(">>> Register endpoint called <<<");

        return authService.register(request);
    }
}