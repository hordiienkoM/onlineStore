package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.UserAlreadyExistException;
import com.hordiienko.onlinestore.repository.UserRepository;
import com.hordiienko.onlinestore.service.OrderService;
import com.hordiienko.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

//    to delete ****************************
    @PostMapping("/add")
    public ResponseEntity registration (@RequestBody User user){
        try {
            userService.registration(user);
            return ResponseEntity.ok("User saved successfully");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//    ***************************************

    @GetMapping("/test")
    public ResponseEntity getOrders() {
        try {
            return ResponseEntity.ok("Server works");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Server error");
        }
    }

    @GetMapping("/orders")
    public ResponseEntity getOrders (@RequestParam Long userId){
        try {
            Set<Order> orders = orderService.getOrders(userId);
            return ResponseEntity.ok().body(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
