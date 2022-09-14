package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.*;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.exception.OrderSaveException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.mapper.OrderMapper;
import com.hordiienko.onlinestore.service.OrderService;
import com.hordiienko.onlinestore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping("/order")
    public ResponseEntity createOrder(@RequestParam Long userId, @RequestBody OrderPostDTO orderBody) {
        try {
            Order order = orderMapper.postDtoToOrder(orderBody);
            orderService.saveOrder(order, userId, orderBody.getOrderProduct());
            return ResponseEntity.ok().body("Order has been saved");
        } catch (OrderSaveException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/id")
    public ResponseEntity deleteOrder(@RequestParam Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok().body("Order has been deleted");
        } catch (OrderNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/order/id")
    public ResponseEntity updateOrder(
            @RequestParam Long orderId,
            @RequestParam Long userId,
            @RequestBody OrderPostDTO orderBody) {
        try {
            Order order = orderMapper.postDtoToOrder(orderBody);
            orderService.updateOrder(order, userId, orderBody, orderId);
            return ResponseEntity.ok().body("Order has been updated");
        } catch (UserNotFoundException|OrderSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/date")
    public ResponseEntity getDate(){
        return ResponseEntity.ok().body(LocalDateTime.now());
    }
}
