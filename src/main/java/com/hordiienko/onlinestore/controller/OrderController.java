package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/date")
    public ResponseEntity getDate (@RequestParam Long orderId){
        try {
            return ResponseEntity.ok().body(orderService.getDate(orderId));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity getStatus (@RequestParam Long orderId){
        try {
            return ResponseEntity.ok().body(orderService.getStatus(orderId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity getUser (@RequestParam Long orderId){
        try {
            return ResponseEntity.ok().body(orderService.getUser(orderId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
