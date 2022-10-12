package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.*;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.exception.OrderSaveException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.mapper.OrderMapper;
import com.hordiienko.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/v1/orders")
@Validated
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;


    @GetMapping()
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity getOrdersPage(Authentication authentication, Pageable pageable) {
        return ResponseEntity.ok().body(orderMapper.toOrdersGetDTO(
                orderService.getByUserId(pageable, authentication)
        ));
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity getOrder(@PathVariable Long orderId, Authentication authentication) {
        try {
            return ResponseEntity.ok().body(orderMapper.toOrderFieldsGetDTO(
                    orderService.getOrder(orderId, authentication)
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Order not found");
        }
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity createOrder(@RequestBody @Valid OrderPostDTO orderBody, Authentication authentication) {
        try {
            Order order = orderMapper.postDtoToOrder(orderBody);
            order = orderService.saveOrder(order, orderBody.getOrderProduct(), authentication);
            return ResponseEntity.ok().body(
                    orderMapper.toOrderGetDTO(order));
        } catch (OrderSaveException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity deleteOrder(@PathVariable Long orderId, Authentication authentication) {
        try {
            orderService.deleteOrder(orderId, authentication);
            return ResponseEntity.ok().body("Order has been deleted");
        } catch (OrderNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity updateOrder(@PathVariable Long orderId,
                                      @RequestBody OrderPostDTO orderBody,
                                      Authentication authentication) {
        try {
            Order order = orderMapper.postDtoToOrder(orderBody);
            orderService.updateOrder(order, orderBody, orderId, authentication);
            return ResponseEntity.ok().body("Order has been updated");
        } catch (UserNotFoundException | OrderSaveException e) {
            throw new RuntimeException(e);
        }
    }

    //    to delete
    @GetMapping("/test")
    public ResponseEntity testAuthorize(@RequestParam String message) {
        return ResponseEntity.ok().body(message);
    }
}
