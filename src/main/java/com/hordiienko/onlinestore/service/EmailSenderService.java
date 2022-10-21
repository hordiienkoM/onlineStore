package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.EmailMessageException;
import com.hordiienko.onlinestore.service.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    @Resource
    private Environment environment;

    public void sendMessageRegistered(User user, Locale locale) {
        MimeMessage message = emailSender.createMimeMessage();
        String htmlContent;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Context context = new Context(locale);
            context.setVariable("name", user.getUsername());
            String pathToConfirm = Objects.requireNonNull(environment.getProperty(
                    "path.continue_registration")) + "?token=" + user.getToken()
                    + "&username=" + user.getUsername();
            context.setVariable("continue_registration", pathToConfirm);
            helper.setFrom(
                    Objects.requireNonNull(environment.getProperty("spring.mail.username"))
            );
            helper.setTo(user.getUsername());
            helper.setSubject("Registration on the site Online Store");
            htmlContent = templateEngine.process("welcome_page.html", context);
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            throw new EmailMessageException();
        }

        log.info("Sending email to: {}", user.getUsername());
        emailSender.send(message);
    }

    public void sendMessageNewOrder(User user, Order order) {
        MimeMessage message = emailSender.createMimeMessage();
        String htmlContent;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariable("name", user.getUsername());
            context.setVariable("productsList", getProductList(order));
            helper.setFrom(
                    Objects.requireNonNull(environment.getProperty("spring.mail.username"))
            );
            helper.setTo(user.getUsername());
            helper.setSubject("New order");
            htmlContent = templateEngine.process("created_order_email.html", context);
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            throw new EmailMessageException();
        }

        log.info("Sending email to: {}", user.getUsername());
        emailSender.send(message);
    }

    public void sendMessageYouWasAdded(User user, Locale locale) {
        MimeMessage message = emailSender.createMimeMessage();
        String htmlContent;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Context context = new Context(locale);
            context.setVariable("name", user.getUsername());
            String pathToConfirm = Objects.requireNonNull(environment.getProperty(
                    "path.continue_registration")) + "?token=" + user.getToken()
                    + "&username=" + user.getUsername();
            context.setVariable("continue_registration", pathToConfirm);
            context.setVariable("temp_password", user.getPassword());
            helper.setFrom(
                    Objects.requireNonNull(environment.getProperty("spring.mail.username"))
            );
            helper.setTo(user.getUsername());
            helper.setSubject("You have been added to list of users on the site Online Store");
            htmlContent = templateEngine.process("you_was_added_email.html", context);
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            throw new EmailMessageException();
        }
        log.info("Sending email to: {}", user.getUsername());
        emailSender.send(message);
    }

    protected String getProductList(Order order) {
        Set<OrderProduct> orderProduct = order.getOrderProduct();
        StringBuilder productList = new StringBuilder();
        orderProduct
                .forEach(el -> productList.append(
                                el.getProduct()
                                        .getDescription())
                        .append(": ")
                        .append(el.getAmount()).append("\n")
                );
        return productList.toString();
    }
}