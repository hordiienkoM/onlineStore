package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.OrderPostDTO;
import com.hordiienko.onlinestore.dto.OrderProductPostDTO;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.exception.OrderSaveException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.repository.OrderProductRepository;
import com.hordiienko.onlinestore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private OrderProductRepository orderProductRepository;

    public Order saveOrder(Order order, Set<OrderProductPostDTO> products, Long userId) throws OrderSaveException {
        try {
            order.setUser(userService.getUser(userId));
            Set<OrderProduct> orderProducts = orderProductService.convert(products, order);
            order.setOrderProduct(orderProducts);
            orderRepository.save(order);
            return order;
        } catch (Exception e) {
            throw new OrderSaveException();
        }
    }

    public void deleteOrder(Long orderId) throws OrderNotFoundException {
        try {
            orderRepository.deleteById(orderId);
        } catch (Exception e) {
            throw new OrderNotFoundException();
        }
    }

    public void updateOrder(Order order, Long userId, OrderPostDTO orderBody, Long orderId)
            throws UserNotFoundException, OrderSaveException {
        order.setUser(userService.getUser(userId));
        order.setId(orderId);
        orderProductRepository.deleteAll(orderProductRepository.findAllByOrderId(orderId));
        Set<OrderProduct> orderProducts = orderProductService.convert(orderBody.getOrderProduct(), order);
        order.setOrderProduct(orderProducts);
        orderRepository.save(order);
    }

    public Order getOrder(Long orderId){
        return orderRepository.findById(orderId).orElseThrow();
    }
}
