package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.OrderPostDTO;
import com.hordiienko.onlinestore.dto.TestDTO;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.exception.OrderSaveException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.mapper.OrderMapper;
import com.hordiienko.onlinestore.mapper.UserMapper;
import com.hordiienko.onlinestore.service.OrderService;
import com.hordiienko.onlinestore.service.UserService;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapping;
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

    @PostMapping("/newOrder")
    public ResponseEntity createOrder(@RequestParam Long userId, @RequestBody OrderPostDTO orderPostDTO){
        try {
            OrderPostDTO orderPostDTO1 = orderPostDTO;
            Order order = orderMapper.toOrder(orderPostDTO1);
            order.setUser(userService.getUser(userId));
            orderService.saveOrder(order);
            return ResponseEntity.ok().body("Order has been saved");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/createTest")
    public ResponseEntity createTest(@RequestBody TestDTO testDTO){
        try {
            TestDTO saved = testDTO;
            return ResponseEntity.ok().body(saved);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
