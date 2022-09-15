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

    //    the orderProduct is saved after the Order is created, because the Order has no index before saving
    public void saveOrder(Order order, Set<OrderProductPostDTO> products) throws OrderSaveException {
        try {
            orderRepository.save(order);
            orderProductService.saveOrderProducts(products, order);
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
        Set<OrderProduct> orderProducts = orderProductService.converter(orderBody.getOrderProduct(), order);
        order.setOrderProduct(orderProducts);
        orderRepository.save(order);
    }
}
