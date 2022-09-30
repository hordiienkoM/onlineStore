package com.hordiienko.onlinestore.service.util;


import com.hordiienko.onlinestore.dto.authorization.UserDetailsImpl;
import org.springframework.security.core.Authentication;

public class SessionUtil {
    public static Long getCurrentUserId(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUserId();
    }
}
