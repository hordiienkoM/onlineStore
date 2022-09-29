package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    public Long getCurrentUserId() {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        return userService.findByUsername(username).getId();
    }

    public User getCurrenUser(){
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        return userRepository.findByUsername(username);
    }
}
