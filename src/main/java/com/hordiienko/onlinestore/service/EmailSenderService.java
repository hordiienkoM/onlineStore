package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.authorization.UserDetailsImpl;
import com.hordiienko.onlinestore.service.util.HtmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("testmikhma@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }


    public void sendMessage(String toEmail, String subject, Authentication authentication) throws MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("mikhailgordiyenko1994@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject(subject);
        String defaultPage = HtmlReader.readWelcomePage();
        String personalizedPage = getPersonalizedPage(defaultPage, authentication);

        mimeMessage.setContent(personalizedPage, "text/html; charset=utf-8");

        mailSender.send(mimeMessage);
    }

    public String getPersonalizedPage(String defaultPage, Authentication authentication) {
        String[] divided = defaultPage.split("split");
        StringBuilder personalizedPage = new StringBuilder();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        String username = user.getUsername();
        String token = user.getToken();
        personalizedPage.append(divided[0]);
        personalizedPage.append(username);
        personalizedPage.append(divided[1]);
        personalizedPage.append(token);
        personalizedPage.append(divided[2]);
        return personalizedPage.toString();
    }


}
