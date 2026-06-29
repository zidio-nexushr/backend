package com.zidio.nexus_hr.authservice.service;

import com.zidio.nexus_hr.authservice.dto.RegisterRequest;
import com.zidio.nexus_hr.authservice.dto.RegisterResponse;
import com.zidio.nexus_hr.authservice.entity.User;
import com.zidio.nexus_hr.authservice.repository.UserRepository;
import com.zidio.nexus_hr.authservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public RegisterResponse register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create user
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        // Save user
        userRepository.save(user);

        // Generate JWT
        String token = jwtUtil.generateToken(user);

        // Return response
        return RegisterResponse.builder()
                .message("Registration successful")
                .token(token)
                .build();
    }
    public String authenticateUser(String username, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);

        return jwtUtil.generateToken(userDetails);
    }
}

