package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.UserConfirmDTO;
import com.hordiienko.onlinestore.dto.authorization.UserDetailsImpl;
import com.hordiienko.onlinestore.entity.Role;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.CodeNotMatchException;
import com.hordiienko.onlinestore.exception.UserAlreadyExistException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.repository.UserRepository;
import com.hordiienko.onlinestore.service.util.PasswordGenerator;
import com.hordiienko.onlinestore.service.util.TokenUtil;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
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

    public User findByUsername(String username, Locale locale) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(locale)
        );
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
        user.setLocale(locale);
        userRepository.save(user);
        emailSenderService.sendMessageRegistered(user, locale);
    }

    public void confirmRegistration(UserConfirmDTO confirm, Locale locale) {
        User user = findByUsername(confirm.getUsername(), locale);
        if (!user.getToken().equals(confirm.getToken())) {
            throw new CodeNotMatchException(locale);
        }
        user.setEnabled(true);
        userRepository.save(user);
    }

    public boolean checkUserEnabled(String username, Locale locale) {
        User user = findByUsername(username, locale);
        return user.isEnabled();
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

    public void checkLocale(Authentication authentication, Locale locale) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        if (user.getLocale().equals(locale)) {
            return;
        }
        user.setLocale(locale);
        userRepository.save(user);
    }
}
