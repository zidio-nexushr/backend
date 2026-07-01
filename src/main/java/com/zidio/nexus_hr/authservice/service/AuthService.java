package com.zidio.nexus_hr.authservice.service;

import com.zidio.nexus_hr.authservice.dto.*;
import com.zidio.nexus_hr.authservice.entity.User;
import com.zidio.nexus_hr.authservice.repository.UserRepository;
import com.zidio.nexus_hr.authservice.security.JwtUtil;
import com.zidio.nexus_hr.authservice.security.TokenBlockService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailLogService emailLogService;

    @Autowired
    private TokenBlockService tokenBlockService;

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


        System.out.println(login.getUserEmail()+"Password\n");
        //get user by email
        User user = userRepository.findByEmail(login.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        //Compare the against the encoded passwords
        if(!passwordEncoder.matches(login.getPassword(), user.getPassword())){
            throw new RuntimeException("invalid credentials");
        }

        return jwtUtil.generateToken(user);
    }

    //Forgot Password
    public void forgotPasswrord(ForgotPasswordDTO forgotPasswordDTO){

        //check if user exist
        User user = userRepository.findByEmail(forgotPasswordDTO.getEmail()).orElseThrow(()-> new RuntimeException("User not found"));

        //generate token
        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        user.setResetTokenExpiry(new Date(System.currentTimeMillis()+10*60*1000L));

        //save the user reset password token
        userRepository.save(user);

        //send token to the user
        emailLogService.sendPasswordResetMail(forgotPasswordDTO.email, token);
    }

    public void resetPassword(ResetPasswordDTO resetPasswordDTO){

        //get user by token
        User user = userRepository.findByResetToken(resetPasswordDTO.getToken())
                .orElseThrow(()-> new RuntimeException("invalid token"));

        //check if the user token has expired
        if(user.getResetTokenExpiry().before(new Date())){
            throw new RuntimeException("Token expired");
        }

        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);

    }

    public String logout(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = jwtUtil.extractToken(header);

        if(token != null){
            tokenBlockService.blockToken(token);
        }

        return "Logged out successful";
    }

}