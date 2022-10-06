package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.UserPostDTO;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.mapper.UserMapper;
import com.hordiienko.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/confirm")
    public ResponseEntity confirmRegistration(@RequestParam String username, @RequestParam String token) {
        try{
            userService.confirmRegistration(username, token);
            return ResponseEntity.ok().body("registration completed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity saveNewUser(@RequestBody UserPostDTO newUser){
        try {
            User user = userMapper.toUser(newUser);
            userService.registrationNewUser(user);
            return ResponseEntity.ok().body("User was saved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User with this name already exist");
        }
    }

}
