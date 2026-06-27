package com.zidio.nexus_hr.authservice.security;

import com.zidio.nexus_hr.authservice.entity.User;
import com.zidio.nexus_hr.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomeUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByEmail(String email){

        //get user by email
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), null);
    }




}
