package com.zidio.nexus_hr.authservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailLogDTO {

    public String recipientEmail;
    public String subject;
    public String body;
    public boolean sentStatus;
}
