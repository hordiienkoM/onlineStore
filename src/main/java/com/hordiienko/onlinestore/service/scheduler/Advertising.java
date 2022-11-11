package com.hordiienko.onlinestore.service.scheduler;

import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.service.EmailSenderService;
import com.hordiienko.onlinestore.service.ProductService;
import com.hordiienko.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Component
public class Advertising {
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Scheduled(cron = "0 0 16 * * THU")
    @Transactional
    public void reportCurrentTime() {
        Locale locale = Locale.getDefault();
        Set<User> usersHasOrders = userService.findUsersHasOrders();
        Map<User, String> recommendations = productService
                .findProductRecommendations(usersHasOrders, locale);
        recommendations.forEach((user, recommendation) -> {
            emailSenderService.sendMessageAdvertising(user, recommendation);
        });
    }
}
