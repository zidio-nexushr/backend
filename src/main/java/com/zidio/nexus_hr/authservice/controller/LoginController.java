
package com.zidio.nexus_hr.authservice.controller;

import com.zidio.nexus_hr.authservice.dto.LoginRequest;
import com.zidio.nexus_hr.authservice.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        String token = loginService.authenticateUser(
                request.getUsername(),
                request.getPassword());

        return ResponseEntity.ok(token);
    }
}