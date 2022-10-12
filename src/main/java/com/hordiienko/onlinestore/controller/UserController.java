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

@RestController
@RequestMapping("/v1/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/confirm")
    public ResponseEntity confirmRegistration(@Valid @RequestBody UserConfirmDTO confirm) {
        try {
            userService.confirmRegistration(confirm);
            return ResponseEntity.ok().body("registration completed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity registrationUser(@Valid @RequestBody UserPostDTO newUser) {
        try {
            User user = userMapper.toUser(newUser);
            userService.registrationUser(user);
            return ResponseEntity.ok().body("User was saved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/enable")
    public ResponseEntity checkEnabled(
            @RequestParam("username") @Email String username
    ) {
        return ResponseEntity.ok(userService.checkUserEnabled(username));
    }

    //    the method to delete
    @DeleteMapping()
    public ResponseEntity deleteById(@RequestParam Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok().body("user was deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
