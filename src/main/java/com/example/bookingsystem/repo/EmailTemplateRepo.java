package com.example.bookingsystem.repo;

import com.example.bookingsystem.modell.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTemplateRepo extends JpaRepository<EmailTemplate, Long> {
}