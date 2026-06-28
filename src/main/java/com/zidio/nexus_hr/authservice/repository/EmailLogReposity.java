package com.zidio.nexus_hr.authservice.repository;


import com.zidio.nexus_hr.authservice.entity.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLogReposity extends JpaRepository<EmailLog, Long> {

}
