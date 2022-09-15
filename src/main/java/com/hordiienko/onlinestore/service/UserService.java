package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public Set<Order> getOrders(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getOrders();
    }
}
