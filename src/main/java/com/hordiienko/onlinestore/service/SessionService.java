package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.authorization.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    public Long getCurrentUserId(Authentication authentication) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return user.getUserId();
    }
}
