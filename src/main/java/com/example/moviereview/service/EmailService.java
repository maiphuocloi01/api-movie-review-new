package com.example.moviereview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Async
    public void send(String to, String context) {
        MimeMessage message = mailSender.createMimeMessage();

        boolean multipart = true;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
            message.setContent(context, "text/html");
            helper.setTo(to);
            helper.setSubject("VieWie: OTP token to confirm account");
        } catch (MessagingException e) {
            throw new IllegalStateException("failed to send email");
        }





//        this.emailSender.send(message);
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("spring.moviereviews@gmail.com");
//        message.setTo(to);
//        message.setText(text);
//        message.setSubject("Confirm token");

        mailSender.send(message);

    }
}

