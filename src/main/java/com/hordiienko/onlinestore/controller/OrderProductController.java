package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.OrderProductInfoGetDTO;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.mapper.OrderProductMapper;
import com.hordiienko.onlinestore.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/order_products")
public class OrderProductController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderProductMapper orderProductMapper;

    @GetMapping("/{orderId}")
    public ResponseEntity getByOrderId(@PathVariable Long orderId){
        Set<OrderProduct> orderProducts = orderService.getProductsByOrderId(orderId);
        Set<OrderProductInfoGetDTO> products = orderProductMapper.toOrderProductInfoGetDTOs(orderProducts);
        return ResponseEntity.ok().body(products);
    }


}


