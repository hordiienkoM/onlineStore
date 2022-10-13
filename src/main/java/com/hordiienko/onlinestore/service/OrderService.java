package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.OrderPostDTO;
import com.hordiienko.onlinestore.dto.OrderProductPostDTO;
import com.hordiienko.onlinestore.dto.authorization.UserDetailsImpl;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.entity.enums.Status;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.exception.OrderSaveException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.repository.OrderProductRepository;
import com.hordiienko.onlinestore.repository.OrderRepository;
import com.hordiienko.onlinestore.repository.UserRepository;
import com.hordiienko.onlinestore.service.util.SessionUtil;
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

    public Order saveOrder(Order order, Set<OrderProductPostDTO> products, Authentication authentication)
            throws OrderSaveException {
        try {
            Long currentUserId = SessionUtil.getCurrentUserId(authentication);
            order.setUser(userRepository.findById(currentUserId).orElseThrow());
            order.setStatus(Status.NEW);
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
            Long currentUserId = SessionUtil.getCurrentUserId(authentication);
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

    public void updateOrder(Order order, OrderPostDTO orderBody, Long orderId, Authentication authentication)
            throws UserNotFoundException, OrderSaveException {
        Order lastOrder = orderRepository.findById(orderId).orElseThrow();
        Long userId = SessionUtil.getCurrentUserId(authentication);
        if (!checkUserHasOrder(lastOrder, userId)) {
            throw new OrderSaveException();
        }
        order.setId(orderId);
        order.setStatus(Status.UPDATED);
        order.setUser(userService.getUser(userId));
        orderProductRepository.deleteAll(orderProductRepository.findAllByOrderId(orderId));
        Set<OrderProduct> orderProducts = orderProductService.convert(orderBody.getOrderProduct(), order);
        order.setOrderProduct(orderProducts);
        orderRepository.save(order);
    }

    public Order getOrder(Long orderId, Authentication authentication) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Long currentUserId = SessionUtil.getCurrentUserId(authentication);
        if (checkUserHasOrder(order, currentUserId)) {
            return order;
        } else {
            throw new Exception("Order not found");
        }
    }

    public boolean checkUserHasOrder(Order order, Long currentUserId) {
        Long orderUserId = order.getUser().getId();
        return orderUserId.equals(currentUserId);
    }

    public Page<Order> getByUserId(Pageable pageable, Authentication authentication) {
        Long currentUserId = SessionUtil.getCurrentUserId(authentication);
        return orderRepository.findAllByUserId(currentUserId, pageable);
    }

    public Set<OrderProduct> getProductsByOrderId(Long orderId, Authentication authentication) throws Exception {
        return getOrder(orderId, authentication).getOrderProduct();
    }
}
