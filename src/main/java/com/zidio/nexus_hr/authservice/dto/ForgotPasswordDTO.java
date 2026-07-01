package com.zidio.nexus_hr.authservice.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgotPasswordDTO {
    public String email;
}
