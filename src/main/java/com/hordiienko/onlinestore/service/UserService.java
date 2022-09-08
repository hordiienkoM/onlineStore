package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.UserAlreadyExistException;
import com.hordiienko.onlinestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registration (User user) throws UserAlreadyExistException {
        if (userRepository.findUserByName(user.getName()) != null) {
            throw  new UserAlreadyExistException();
        }
        return userRepository.save(user);
    }
}
