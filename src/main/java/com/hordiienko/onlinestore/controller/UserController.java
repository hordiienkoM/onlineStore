package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private OrderService orderService;

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
    public ResponseEntity getOrders (@RequestParam Long userId){
        try {
            Set<Order> orders = orderService.getOrders(userId);
            return ResponseEntity.ok().body(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
