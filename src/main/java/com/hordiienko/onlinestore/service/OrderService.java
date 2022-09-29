package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.dto.OrderPostDTO;
import com.hordiienko.onlinestore.dto.OrderProductPostDTO;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.exception.OrderSaveException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.repository.OrderProductRepository;
import com.hordiienko.onlinestore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private SessionService sessionService;

    public Order saveOrder(Order order, Set<OrderProductPostDTO> products) throws OrderSaveException {
        try {
            order.setUser(userService.getUser(sessionService.getCurrentUserId()));
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
            Order order = orderRepository.findById(orderId).orElseThrow();
            if (checkUserHasOrder(order)) {
                orderRepository.deleteById(orderId);
            } else {
                throw new Exception();
            }
//            orderRepository.deleteById(orderId);
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

    public Order getOrder(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow();
        if(checkUserHasOrder(order)){
            return order;
        } else {
            throw new Exception("Order not found");
        }
    }

    public boolean checkUserHasOrder(Order order){
        Long orderUserId = order.getUser().getId();
        Long currentUserId = sessionService.getCurrenUser().getId();
        return orderUserId.equals(currentUserId);
    }

    public Page<Order> getByUserId(Pageable pageable){
        Long userId = sessionService.getCurrentUserId();
        return orderRepository.findAllByUserId(userId, pageable);
    }

    public Set<OrderProduct> getProductsByOrderId(Long orderId) throws Exception {
        return getOrder(orderId).getOrderProduct();
    }
}
