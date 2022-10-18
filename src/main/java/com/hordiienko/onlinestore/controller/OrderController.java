package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.*;
import com.hordiienko.onlinestore.entity.Order;
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
import javax.validation.constraints.Min;
import java.util.Locale;


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
        return ResponseEntity.ok().body(
                orderService.getByUserId(pageable, authentication)
                        .map(orderMapper::toOrderGetDTO)
        );
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<OrderGetInfoDTO> getOrder(@PathVariable @Min(1) Long orderId,
                                                    Authentication authentication,
                                                    Locale locale) {
        return ResponseEntity.ok().body(orderMapper.toOrderGetInfoDTO(
                orderService.getOrder(orderId, authentication, locale)
        ));
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<OrderGetDTO> createOrder(@RequestBody @Valid OrderPostDTO orderBody,
                                                   Authentication authentication) {
        Order order = orderMapper.postDtoToOrder(orderBody);
        order = orderService.saveOrder(order, orderBody.getOrderProduct(), authentication);
        return ResponseEntity.ok().body(
                orderMapper.toOrderGetDTO(order));
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId,
                                              Authentication authentication,
                                              Locale locale) {
        orderService.deleteOrder(orderId, authentication, locale);
        return ResponseEntity.ok().body("Order has been deleted");
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<String> updateOrder(@PathVariable Long orderId,
                                              @RequestBody OrderPostDTO orderBody,
                                              Authentication authentication,
                                              Locale locale) {
        Order order = orderMapper.postDtoToOrder(orderBody);
        orderService.updateOrder(
                order,
                orderBody,
                orderId,
                authentication,
                locale
        );
        return ResponseEntity.ok().body("Order has been updated");
    }
}
