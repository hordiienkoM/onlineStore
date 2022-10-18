package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.UserConfirmDTO;
import com.hordiienko.onlinestore.dto.UserPostDTO;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.mapper.UserMapper;
import com.hordiienko.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.Locale;

@RestController
@RequestMapping("/v1/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/confirm")
    public ResponseEntity confirmRegistration(@Valid @RequestBody UserConfirmDTO confirm, Locale locale) {
        userService.confirmRegistration(confirm, locale);
        return ResponseEntity.ok().body("registration completed successfully");
    }

    @PostMapping()
    public ResponseEntity registrationUser(@Valid @RequestBody UserPostDTO newUser, Locale locale) {
        User user = userMapper.toUser(newUser);
        userService.registrationUser(user, locale);
        return ResponseEntity.ok().body("User was saved");
    }

    @GetMapping("/enable")
    public ResponseEntity checkEnabled(
            @RequestParam("username") @Email String username, Locale locale
    ) {
        return ResponseEntity.ok(
                userService.checkUserEnabled(username, locale)
        );
    }
}
