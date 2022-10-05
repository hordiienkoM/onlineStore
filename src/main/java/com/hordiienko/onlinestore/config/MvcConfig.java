package com.hordiienko.onlinestore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/online_shop").setViewName("online_shop");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin_page").setViewName("admin_page");
        registry.addViewController("/confirm_registration").setViewName("confirm_registration");
        registry.addViewController("/registration").setViewName("registration");
    }
}