package com.zidio.nexus_hr.authservice.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDTO {
    private String token;
    private String password;
}
