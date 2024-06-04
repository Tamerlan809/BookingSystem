package com.example.bookingsystem.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import com.example.bookingsystem.modell.Bokning;
import com.example.bookingsystem.modell.EmailTemplate;
import com.example.bookingsystem.repo.EmailTemplateRepo;
import com.example.bookingsystem.util.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailTemplateRepo emailTemplateRepo;

    public void sendBookingConfirmationEmail(Bokning booking) {
        List<EmailTemplate> templates = emailTemplateRepo.findAll();
        EmailTemplate emailTemplate = templates.get(0);

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("namn", booking.getKund().getNamn());
        placeholders.put("room", booking.getRoom().getName());
        placeholders.put("nights", String.valueOf(booking.getNights()));
        placeholders.put("startdate", booking.getStartDate().toString());
        placeholders.put("enddate", booking.getEndDate().toString());
        placeholders.put("totalPrice", String.valueOf(booking.getTotalPrice()));

        String subject = TemplateUtil.replacePlaceholders(emailTemplate.getSubject(), placeholders);
        String body = TemplateUtil.replacePlaceholders(emailTemplate.getBody(), placeholders);

        String htmlBody = "<!DOCTYPE html>"
                + "<html><head><style>"
                + "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f4f4f4; margin: 0; padding: 0; }"
                + ".container { width: 80%; margin: 20px auto; padding: 20px; background-color: #fff; border-radius: 10px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); }"
                + ".header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; border-top-left-radius: 10px; border-top-right-radius: 10px; }"
                + ".header h1 { margin: 0; font-size: 24px; }"
                + ".content { padding: 20px; }"
                + ".content p { margin: 0 0 10px; }"
                + ".content ul { list-style-type: none; padding: 0; margin: 20px 0; }"
                + ".content li { margin-bottom: 10px; font-size: 16px; }"
                + ".content li strong { display: inline-block; width: 150px; }"
                + ".footer { text-align: center; padding: 10px; margin-top: 20px; font-size: 14px; color: #777; background-color: #f1f1f1; border-bottom-left-radius: 10px; border-bottom-right-radius: 10px; }"
                + "</style></head><body>"
                + "<div class='container'>"
                + "<div class='header'><h1>Bokningsbekräftelse</h1></div>"
                + "<div class='content'>"
                + body
                + "</div>"
                + "<div class='footer'><p>Hotell AB | Adressvägen 123, 123 45 Staden | Tel: 012-345 678</p></div>"
                + "</div>"
                + "</body></html>";


        String styledBody = TemplateUtil.replacePlaceholders(htmlBody, placeholders);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setTo(booking.getKund().getEmail());
            helper.setSubject(subject);
            helper.setText(styledBody, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        mailSender.send(mimeMessage);
    }

    public void sendPasswordResetEmail(String email, String resetLink) {
        String subject = "Återställ ditt lösenord";
        String body = "<p>Klicka på länken nedan för att återställa ditt lösenord:</p>"
                + "<p><a href=\"" + resetLink + "\">" + resetLink + "</a></p>"
                + "<p>Länken är giltig i 24 timmar.</p>";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        mailSender.send(mimeMessage);
    }
    }