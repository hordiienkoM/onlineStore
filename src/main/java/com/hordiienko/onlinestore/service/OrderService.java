package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.OrderPostDTO;
import com.hordiienko.onlinestore.dto.OrderProductPostDTO;
import com.hordiienko.onlinestore.dto.authorization.UserDetailsImpl;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.exception.OrderSaveException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.repository.OrderProductRepository;
import com.hordiienko.onlinestore.repository.OrderRepository;
import com.hordiienko.onlinestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private UserRepository userRepository;

    public Order saveOrder(Order order, Set<OrderProductPostDTO> products, Authentication authentication) throws OrderSaveException {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            order.setUser(userRepository.findById(userDetails.getUserId()).orElseThrow());
            Set<OrderProduct> orderProducts = orderProductService.convert(products, order);
            order.setOrderProduct(orderProducts);
            orderRepository.save(order);
            return order;
        } catch (Exception e) {
            throw new OrderSaveException();
        }
    }

    public void deleteOrder(Long orderId, Authentication authentication) throws OrderNotFoundException {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Order order = orderRepository.findById(orderId).orElseThrow();
            if (checkUserHasOrder(order, userDetails.getUserId())) {
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

    public Order getOrder(Long orderId, Authentication authentication) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if(checkUserHasOrder(order, userDetails.getUserId())){
            return order;
        } else {
            throw new Exception("Order not found");
        }
    }

    public boolean checkUserHasOrder(Order order, Long currentUserId){
        Long orderUserId = order.getUser().getId();
        return orderUserId.equals(currentUserId);
    }

    public Page<Order> getByUserId(Pageable pageable, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return orderRepository.findAllByUserId(userDetails.getUserId(), pageable);
    }

    public Set<OrderProduct> getProductsByOrderId(Long orderId, Authentication authentication) throws Exception {
        return getOrder(orderId, authentication).getOrderProduct();
    }
}
