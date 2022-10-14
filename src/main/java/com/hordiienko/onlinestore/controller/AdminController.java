package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUserById(@RequestParam Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().body("user was deleted");
    }
}
