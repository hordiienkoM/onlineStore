package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.*;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.mapper.OrderMapper;
import com.hordiienko.onlinestore.service.OrderProductService;
import com.hordiienko.onlinestore.service.OrderService;
import com.hordiienko.onlinestore.service.ProductService;
import com.hordiienko.onlinestore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
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
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderProductService orderProductService;


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
    public ResponseEntity createOrder(@RequestParam Long userId,
                                      @RequestBody OrderPostDTO orderPostDTO
    ){
        try {
            Order order = orderMapper.postDtoToOrder(orderPostDTO);
            order.setUser(userService.getUser(userId));
            orderService.saveOrder(order);
            Set<OrderProductPostDTO> orderProductDTOs = orderPostDTO.getOrderProduct();
            orderProductService.saveOrderProducts(orderProductDTOs, order);
//            for (OrderProductPostDTO orderProductDTO: orderProductDTOs){
//                OrderProduct orderProduct = new OrderProduct();
//                orderProduct.setProduct(productService
//                        .getProduct(orderProductDTO.getProduct().getId()));
//                orderProduct.setAmount(orderProductDTO.getAmount());
//                orderProduct.setOrder(order);
//                orderProductService.saveOrderProduct(orderProduct);
//            }
            return ResponseEntity.ok().body("Order has been saved");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/")
    public ResponseEntity deleteOrder(@RequestParam Long orderId){
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok().body("Order has been deleted");
        } catch (OrderNotFoundException e) {
            return ResponseEntity.badRequest().body("Something wrong during deleting");
        }
    }
}
