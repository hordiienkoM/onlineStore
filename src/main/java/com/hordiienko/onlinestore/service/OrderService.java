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
import com.hordiienko.onlinestore.repository.UserRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserRepository userRepository;

    public Order saveOrder(Order order, Set<OrderProductPostDTO> products, Long userId) throws OrderSaveException {
        try {
            order.setUser(userRepository.findById(userId).orElseThrow());
            Set<OrderProduct> orderProducts = orderProductService.convert(products, order);
            order.setOrderProduct(orderProducts);
            orderRepository.save(order);
            return order;
        } catch (Exception e) {
            throw new OrderSaveException();
        }
    }

    public void deleteOrder(Long orderId, Long currentUserId) throws OrderNotFoundException {
        try {
            Order order = orderRepository.findById(orderId).orElseThrow();
            if (checkUserHasOrder(order, currentUserId)) {
                orderRepository.deleteById(orderId);
            } else {
                throw new Exception();
            }
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

    public Order getOrder(Long orderId, Long currentUserId) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow();
        if(checkUserHasOrder(order, currentUserId)){
            return order;
        } else {
            throw new Exception("Order not found");
        }
    }

    public boolean checkUserHasOrder(Order order, Long currentUserId){
        Long orderUserId = order.getUser().getId();
        return orderUserId.equals(currentUserId);
    }

    public Page<Order> getByUserId(Pageable pageable, Long userId){
        return orderRepository.findAllByUserId(userId, pageable);
    }

    public Set<OrderProduct> getProductsByOrderId(Long orderId, Long currentUserId) throws Exception {
        return getOrder(orderId, currentUserId).getOrderProduct();
    }
}
