package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.service.util.HtmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendMessage(User user) throws MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("mikhailgordiyenko1994@gmail.com");
        mimeMessageHelper.setTo(user.getUsername());
        mimeMessageHelper.setSubject("registration on the site Online Store");
        String defaultPage = HtmlReader.readWelcomePage();
        String personalizedPage = getPersonalizedPage(defaultPage, user);

        mimeMessage.setContent(personalizedPage, "text/html; charset=utf-8");

        mailSender.send(mimeMessage);
    }

    public String getPersonalizedPage(String defaultPage, User user) {
        String[] divided = defaultPage.split("split");
        StringBuilder personalizedPage = new StringBuilder();
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