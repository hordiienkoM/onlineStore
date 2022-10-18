package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.OrderProductGetDTO;
import com.hordiienko.onlinestore.dto.OrderProductInfoGetDTO;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.mapper.OrderProductMapper;
import com.hordiienko.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Set;

@RestController
@RequestMapping("/v1/order_products")
public class OrderProductController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderProductMapper orderProductMapper;

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity getByOrderId(@PathVariable Long orderId, Authentication authentication, Locale locale) {
        Set<OrderProduct> orderProducts = orderService.getProductsByOrderId(orderId, authentication, locale);
        Set<OrderProductInfoGetDTO> products = orderProductMapper.toOrderProductInfoGetDTOs(orderProducts);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/full_info/{orderId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity getDtoByOrderId(@PathVariable Long orderId, Authentication authentication, Locale locale) {
        Set<OrderProduct> orderProducts = orderService.getProductsByOrderId(orderId, authentication, locale);
        Set<OrderProductGetDTO> products = orderProductMapper.toOrderProductGetDTOs(orderProducts);
        return ResponseEntity.ok().body(products);
    }
}


