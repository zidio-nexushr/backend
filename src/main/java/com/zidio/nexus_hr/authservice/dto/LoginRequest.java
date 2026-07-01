package com.zidio.nexus_hr.authservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    private String userEmail;
    private String password;
}
