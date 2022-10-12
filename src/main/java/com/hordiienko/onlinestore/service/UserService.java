package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.UserConfirmDTO;
import com.hordiienko.onlinestore.entity.Role;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.UserAlreadyExistException;
import com.hordiienko.onlinestore.repository.UserRepository;
import com.hordiienko.onlinestore.service.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collections;

// not delete emailSenderService.sendMessage(user)!!!
@Service
@Validated
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

    public synchronized void registrationUser(@Valid User user) throws Exception {
        if (userRepository.existsByUsername(user.getUsername())){
            throw new UserAlreadyExistException();
        }
        String salt = BCrypt.gensalt();
        String hashPassword = BCrypt.hashpw(user.getPassword(), salt);
        user.setPassword(hashPassword);
        user.setRoles(Collections.singleton(new Role(1, "USER_ROLE")));
        user.setToken(TokenUtil.getToken());
        userRepository.save(user);
        emailSenderService.sendHtmlMessage(user);
    }

    public void confirmRegistration(UserConfirmDTO confirm) throws Exception {
        User user = findByUsername(confirm.getUsername());
        if(!user.getToken().equals(confirm.getToken())) {
            throw new Exception("Code not match");
        }
        user.setEnabled(true);
        userRepository.save(user);
    }

    public boolean checkUserEnabled(String username) {
        User user = userRepository.findByUsername(username);
        return user.isEnabled();
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

}
