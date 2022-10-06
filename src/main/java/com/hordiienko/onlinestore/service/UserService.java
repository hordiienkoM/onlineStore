package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.Role;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.UserAlreadyExistException;
import com.hordiienko.onlinestore.repository.UserRepository;
import com.hordiienko.onlinestore.service.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Collections;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailSenderService emailSenderService;

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void registrationNewUser(User user) throws UserAlreadyExistException, MessagingException, IOException {
        if (userRepository.findByUsername(user.getUsername()) != null){
            throw new UserAlreadyExistException();
        } else {
            String salt = BCrypt.gensalt();
            String hashPassword = BCrypt.hashpw(user.getPassword(), salt);
            user.setPassword(hashPassword);
            user.setRoles(Collections.singleton(new Role(1L, "USER_ROLE")));
            user.setPassword(user.getPassword());
            user.setToken(TokenUtil.getToken());
            emailSenderService.sendMessage(user);
            userRepository.save(user);
        }
    }

    public void confirmRegistration(String username, String token) throws Exception {
        User user = findByUsername(username);
        if(!user.getToken().equals(token)) {
            throw new Exception("passwords not match");
        }
        user.setEnabled(true);
        userRepository.save(user);
    }

}
