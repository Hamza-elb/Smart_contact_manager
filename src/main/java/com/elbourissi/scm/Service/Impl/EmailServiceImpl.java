package com.elbourissi.scm.Service.Impl;

import com.elbourissi.scm.Service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.properties.domain_name}")
    private String domainName;

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * @param to
     * @param subject
     * @param body
     */
    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(domainName);
        mailSender.send(message);
    }

    /**
     *
     */
    @Override
    public void sendEmailWithHtml() {

    }

    /**
     *
     */
    @Override
    public void sendEmailWithAttachment() {

    }
}
