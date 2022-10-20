package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.UserConfirmDTO;
import com.hordiienko.onlinestore.dto.authorization.UserDetailsImpl;
import com.hordiienko.onlinestore.entity.Role;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.CodeNotMatchException;
import com.hordiienko.onlinestore.exception.UserAlreadyExistException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.repository.UserRepository;
import com.hordiienko.onlinestore.service.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Locale;

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

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public synchronized void registrationUser(User user, Locale locale) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistException(locale);
        }
        String salt = BCrypt.gensalt();
        String hashPassword = BCrypt.hashpw(user.getPassword(), salt);
        user.setPassword(hashPassword);
        user.setRoles(Collections.singleton(new Role(1L, "USER_ROLE")));
        user.setToken(TokenUtil.getToken());
        userRepository.save(user);
        emailSenderService.sendMessageRegistered(user);
    }

    public void confirmRegistration(UserConfirmDTO confirm, Locale locale) {
        User user;
        try {
            user = findByUsername(confirm.getUsername());
        } catch (Exception e) {
            throw new UserNotFoundException(locale);
        }
        if (!user.getToken().equals(confirm.getToken())) {
            throw new CodeNotMatchException(locale);
        }
        user.setEnabled(true);
        userRepository.save(user);
    }

    public boolean checkUserEnabled(String username, Locale locale) {
        try {
            User user = userRepository.findByUsername(username);
            return user.isEnabled();
        } catch (Exception e) {
            throw new UserNotFoundException(locale);
        }
    }

    public void deleteById(Long id, Locale locale) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new UserNotFoundException(locale);
        }
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
