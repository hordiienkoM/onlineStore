package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.*;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.exception.OrderSaveException;
import com.hordiienko.onlinestore.mapper.OrderMapper;
import com.hordiienko.onlinestore.service.OrderService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@AllArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;


    @GetMapping()
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @ResponseBody
    public ResponseEntity getOrdersPage(Pageable pageable, Principal principal) {
        return ResponseEntity.ok().body(orderMapper.toOrdersGetDTO(
                orderService.getByUserId(pageable, principal.getName())
        ));
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity getOrder(@PathVariable Long orderId){
        try {
            return ResponseEntity.ok().body(orderMapper.toOrderFieldsGetDTO(
                    orderService.getOrder(orderId)
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Order not found");
        }
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity createOrder(@RequestBody OrderPostDTO orderBody) {
        try {
            Order order = orderMapper.postDtoToOrder(orderBody);
            order = orderService.saveOrder(order, orderBody.getOrderProduct());
            return ResponseEntity.ok().body(
                    orderMapper.toOrderGetDTO(order));
        } catch (OrderSaveException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().body("Order has been deleted");
        } catch (OrderNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
