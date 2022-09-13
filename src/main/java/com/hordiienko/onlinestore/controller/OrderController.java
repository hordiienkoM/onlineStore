package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.mapper.OrderMapper;
import com.hordiienko.onlinestore.mapper.UserMapper;
import com.hordiienko.onlinestore.service.OrderService;
import com.hordiienko.onlinestore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderMapper orderMapper;


    @GetMapping("/orders")
    public ResponseEntity getOrders(@RequestParam Long userId) {
        try {
            User user = userService.getUser(userId);
            Set<Order> orders = user.getOrders();
            return ResponseEntity.ok().body(orderMapper.toOrderGetDTOs(orders));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/date")
    public ResponseEntity getDate(@RequestParam Long orderId) {
        try {
            return ResponseEntity.ok().body(orderService.getDate(orderId));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity getStatus(@RequestParam Long orderId) {
        try {
            return ResponseEntity.ok().body(orderService.getStatus(orderId));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/address")
    public ResponseEntity getAddress(@RequestParam Long orderId) {
        try {
            return ResponseEntity.ok().body(orderService.getAddress(orderId));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/products")
    public ResponseEntity getProducts(@RequestParam Long orderId) {
        try {
            return ResponseEntity.ok().body(orderService.getProducts(orderId));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
