package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.*;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.exception.OrderSaveException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.mapper.OrderMapper;
import com.hordiienko.onlinestore.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping()
    public ResponseEntity getOrdersPage(@RequestParam Long userId,
                                        @RequestParam Integer page,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String sortField) {
        return ResponseEntity.ok().body(orderMapper.toOrdersGetDTO(
                orderService.getByUserId(userId, page, pageSize, sortField)
        ));
    }

    @PostMapping()
    public ResponseEntity createOrder(@RequestBody OrderPostDTO orderBody, @RequestParam Long userId) {
        try {
            Order order = orderMapper.postDtoToOrder(orderBody);
            order = orderService.saveOrder(order, orderBody.getOrderProduct(), userId);
            return ResponseEntity.ok().body(
                    orderMapper.toOrderGetDTO(order));
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
            Order order = orderMapper.postDtoToOrder(orderBody);
            orderService.updateOrder(order, userId, orderBody, id);
            return ResponseEntity.ok().body(
                    orderMapper.toOrderGetDTO(orderService.getOrder(id))
            );
        } catch (UserNotFoundException|OrderSaveException e) {
            throw new RuntimeException(e);
        }
    }
}
