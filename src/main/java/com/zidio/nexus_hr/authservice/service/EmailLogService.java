package com.zidio.nexus_hr.authservice.service;

import com.zidio.nexus_hr.authservice.dto.EmailLogDTO;
import com.zidio.nexus_hr.authservice.entity.EmailLog;
import com.zidio.nexus_hr.authservice.repository.EmailLogReposity;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailLogService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailLogReposity emailLogReposity;

    //Get the frontend link
    @Value("${app.frontend.url}")
    private String frontendUrl;

    //reset password function
    public void sendPasswordResetMail(String to, String token){

        String passwordresetLink = frontendUrl + "/auth/reset-password=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Reset your password");
        message.setText("Click the link to reset your password: \n "+ passwordresetLink);

        javaMailSender.send(message);
    }

    public String getNotificationEmail(EmailLogDTO emailLogDTO){

        boolean sentStatus = false;

        try {

            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,true);

            helper.setTo(emailLogDTO.recipientEmail);
            helper.setSubject(emailLogDTO.subject);
            helper.setText(emailLogDTO.body, true);

            javaMailSender.send(message);

            sentStatus=true;

        } catch (Exception e) {
            sentStatus=false;
            throw new RuntimeException("Email not found");
        }

        EmailLog log = new EmailLog(emailLogDTO.recipientEmail, emailLogDTO.subject, emailLogDTO.body, emailLogDTO.sentStatus);
        emailLogReposity.save(log);

        return sentStatus?"Email sent successfully":"Email sending failed";
    }


}
