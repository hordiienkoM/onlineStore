package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.*;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.mapper.OrderMapper;
import com.hordiienko.onlinestore.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "Get orders page",
            notes = "pageable has fields: (int)page, (int)size, (String)sort")
    public ResponseEntity getOrdersPage(Authentication authentication, Pageable pageable) {
        return ResponseEntity.ok().body(
                orderService.getByUserId(pageable, authentication)
                        .map(orderMapper::toOrderGetDTO)
        );
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @ApiOperation("Get  order with id=orderId")
    public ResponseEntity<OrderGetInfoDTO> getOrder(
            @ApiParam(value = "order id number" , required = true , example = "1")
            @PathVariable @Min(1) Long orderId,
            Authentication authentication,
            Locale locale) {
        return ResponseEntity.ok().body(orderMapper.toOrderGetInfoDTO(
                orderService.getOrder(orderId, authentication, locale)
        ));
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @ApiOperation("Create new order")
    public ResponseEntity<OrderGetDTO> createOrder(@RequestBody @Valid OrderPostDTO orderBody,
                                                   Authentication authentication) {
        Order order = orderMapper.postDtoToOrder(orderBody);
        order = orderService.saveOrder(order, orderBody.getOrderProduct(), authentication);
        return ResponseEntity.ok().body(
                orderMapper.toOrderGetDTO(order));
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @ApiOperation("Delete the order if it belongs to this user")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId,
                                              Authentication authentication,
                                              Locale locale) {
        orderService.deleteOrder(orderId, authentication, locale);
        return ResponseEntity.ok().body("Order has been deleted");
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @ApiOperation("Update the order if it belongs to this user")
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
