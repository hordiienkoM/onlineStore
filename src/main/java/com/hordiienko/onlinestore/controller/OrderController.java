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
@RequestMapping("/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderMapper orderMapper;


    @GetMapping()
    public ResponseEntity getOrders(@RequestParam Long userId) {
        return ResponseEntity.ok().body(orderMapper.toOrderGetDTOs(
                userService.getOrders(userId)
        ));
    }

    @PostMapping()
    public ResponseEntity createOrder(@RequestBody OrderPostDTO orderBody) {
        try {
            Order order = orderMapper.postDtoToOrder(orderBody);
            orderService.saveOrder(order, orderBody.getOrderProduct());
            return ResponseEntity.ok().body("Order has been saved");
        } catch (OrderSaveException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().body("Order has been deleted");
        } catch (OrderNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOrder(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestBody OrderPostDTO orderBody) {
        try {
            OrderPostDTO body = orderBody;
            Order order = orderMapper.postDtoToOrder(orderBody);
            orderService.updateOrder(order, userId, orderBody, id);
            return ResponseEntity.ok().body("Order has been updated");
        } catch (UserNotFoundException|OrderSaveException e) {
            throw new RuntimeException(e);
        }
    }
}
