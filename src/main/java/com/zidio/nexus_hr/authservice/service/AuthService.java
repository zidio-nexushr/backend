package com.zidio.nexus_hr.authservice.service;

import com.zidio.nexus_hr.authservice.dto.LoginRequest;
import com.zidio.nexus_hr.authservice.dto.RegisterRequest;
import com.zidio.nexus_hr.authservice.dto.AuthResponse;
import com.zidio.nexus_hr.authservice.entity.User;
import com.zidio.nexus_hr.authservice.repository.UserRepository;
import com.zidio.nexus_hr.authservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create user
        User user = User.builder()
                .firstName(request.getFirst_name())
                .lastName(request.getLast_name())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        // Save user
        userRepository.save(user);

        // Generate JWT
        String token = jwtUtil.generateToken(user);

        // Return response
        return new AuthResponse(token, "User register sucessfully");
    }

    public String login(LoginRequest login) {

        //get user by email
        User user = userRepository.findByEmail(login.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        //Compare the against the encoded passwords
        if(!passwordEncoder.matches(login.getPassword(), user.getPassword())){
            throw new RuntimeException("invalid credentials");
        }

        return jwtUtil.generateToken(user);
    }

}