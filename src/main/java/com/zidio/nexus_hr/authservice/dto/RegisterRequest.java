package com.zidio.nexus_hr.authservice.dto;

import com.zidio.nexus_hr.authservice.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    private String first_name;

    private String last_name;

    private String email;

    private String password;

    private Role role;
}