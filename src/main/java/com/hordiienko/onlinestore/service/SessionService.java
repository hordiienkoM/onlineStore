package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    private UserService userService;
    public Long getCurrentUserId() {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        ;
        return userService.findByUsername(username).getId();
    }
}
