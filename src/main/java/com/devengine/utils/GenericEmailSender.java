package com.devengine.utils;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenericEmailSender {

    private final JavaMailSender mailSender;

    public void sendEmail(String fromEmail, String toEmail, String subject, String body) {

        try {

            if (fromEmail == null || toEmail == null) {
                throw new IllegalArgumentException("From or To email cannot be null");
            }

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);

            log.info("Email sent successfully from {} to {}", fromEmail, toEmail);

        } catch (Exception ex) {

            log.error("Error while sending email from {} to {}", fromEmail, toEmail, ex);

            throw new RuntimeException("Failed to send email", ex);
        }
    }
}