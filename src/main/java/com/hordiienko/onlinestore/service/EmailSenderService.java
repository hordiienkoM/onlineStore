package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.EmailMessageException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    @Resource
    private Environment environment;
    @Autowired
    private OrderProductService orderProductService;

    private void sendMimeMessage(MessageInfo messageInfo) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(
                    Objects.requireNonNull(environment.getProperty("spring.mail.username"))
            );
            helper.setTo(messageInfo.getUsername());
            helper.setSubject(messageInfo.getSubject());
            helper.setText(messageInfo.getHtmlContent(), true);
        } catch (MessagingException e) {
            throw new EmailMessageException(messageInfo.getLocale());
        }
        log.info("Sending email to: {}", messageInfo.getUsername());
        emailSender.send(message);
    }

    @Builder
    @Data
    private static class MessageInfo {
        private String username;
        private String subject;
        private Locale locale;
        private String htmlContent;
    }

    public void sendMessageRegistered(User user, Locale locale) {
        Context context = new Context(locale);
        context.setVariable("name", user.getUsername());
        String pathToConfirm = Objects.requireNonNull(environment.getProperty(
                "path.continue_registration")) + "?token=" + user.getToken()
                + "&username=" + user.getUsername();
        context.setVariable("continue_registration", pathToConfirm);

        String htmlContent = templateEngine.process("welcome_page.html", context);

        MessageInfo messageInfo = MessageInfo.builder()
                .htmlContent(htmlContent)
                .subject("Registration on the site Online Store")
                .locale(locale)
                .username(user.getUsername())
                .build();

        sendMimeMessage(messageInfo);
    }

    public void sendMessageNewOrder(User user, Order order, Locale locale) {
        Context context = new Context();
        context.setVariable("name", user.getUsername());
        context.setVariable(
                "productsList",
                orderProductService.getOrderProductInfo(order, locale)
        );

        String htmlContent = templateEngine.process("created_order_email.html", context);

        MessageInfo messageInfo = MessageInfo.builder()
                .htmlContent(htmlContent)
                .subject("New order")
                .locale(locale)
                .username(user.getUsername())
                .build();

        sendMimeMessage(messageInfo);
    }

    public void sendMessageYouWasAdded(User user, Locale locale) {
        Context context = new Context(locale);
        context.setVariable("name", user.getUsername());
        String pathToConfirm = Objects.requireNonNull(environment.getProperty(
                "path.continue_registration")) + "?token=" + user.getToken()
                + "&username=" + user.getUsername();
        context.setVariable("continue_registration", pathToConfirm);
        context.setVariable("temp_password", user.getPassword());

        String htmlContent = templateEngine.process("you_was_added_email.html", context);

        MessageInfo messageInfo = MessageInfo.builder()
                .htmlContent(htmlContent)
                .subject("You have been added to list of users on the site Online Store")
                .locale(locale)
                .username(user.getUsername())
                .build();

        sendMimeMessage(messageInfo);
    }

    public void sendMessageAdvertising(User user, String recommendedProducts) {
        Context context = new Context(user.getLocale());
        context.setVariable("recommendation", recommendedProducts);
        context.setVariable("name", user.getUsername());

        String htmlContent = templateEngine.process("advertising_email.html", context);

        MessageInfo messageInfo = MessageInfo.builder()
                .htmlContent(htmlContent)
                .subject("New offer!")
                .locale(user.getLocale())
                .username(user.getUsername())
                .build();

        sendMimeMessage(messageInfo);
    }
}