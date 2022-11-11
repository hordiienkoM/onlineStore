package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.UserConfirmDTO;
import com.hordiienko.onlinestore.entity.Role;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.CodeNotMatchException;
import com.hordiienko.onlinestore.exception.UserAlreadyExistException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.repository.UserRepository;
import com.hordiienko.onlinestore.service.util.PasswordGenerator;
import com.hordiienko.onlinestore.service.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

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
        emailSenderService.sendMessageRegistered(user, locale);
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

    public User createUserBlank(String username, Locale locale) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException(locale);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordGenerator.getPassword());
        user.setRoles(Collections.singleton(new Role(1L, "USER_ROLE")));
        user.setToken(TokenUtil.getToken());
        emailSenderService.sendMessageYouWasAdded(user, locale);
        String salt = BCrypt.gensalt();
        String hashPassword = BCrypt.hashpw(user.getPassword(), salt);
        user.setPassword(hashPassword);
        return userRepository.save(user);
    }

    public Set<User> findUsersHasOrders() {
        return userRepository.findByOrdersIsNotNull();
    }
}
