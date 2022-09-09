package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.service.OrderService;
import com.hordiienko.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

//    to delete ****************************
    @GetMapping("/test")
    public ResponseEntity getStatus() {
        try {
            return ResponseEntity.ok("Server works");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Server error");
        }
    }
//    ***************************************

    @GetMapping("/orders")
    public ResponseEntity getOrders (@RequestParam Long userId) throws UserNotFoundException {
        try {
            User user = userService.getUser(userId);
            var l = user.getOrders();
            return ResponseEntity.ok().body(l);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
