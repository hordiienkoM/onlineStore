package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.exception.UserNotFoundException;
import com.hordiienko.onlinestore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;

    public Set<Order> getOrders(Long userId)throws OrderNotFoundException {
        try {
            User user = userService.getUser(userId);
            return user.getOrders();
        } catch (Exception e) {
            throw  new OrderNotFoundException();
        }
    }

    public GregorianCalendar getDate(Long id) throws OrderNotFoundException {
        try {
            return orderRepository.findById(id).get().getCreateDate();
        } catch (Exception e) {
            throw  new OrderNotFoundException();
        }
    }

    public String getStatus(Long id) throws OrderNotFoundException {
        try {
            return orderRepository.findById(id).get().getStatus();
        } catch (Exception e) {
            throw  new OrderNotFoundException();
        }
    }

    public User getUser(Long orderId) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isPresent()){
            return order.get().getUser();
        }
        throw new OrderNotFoundException();
    }

    public String getAddress(Long orderId) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isPresent()){
            return order.get().getAddress();
        }
        throw new OrderNotFoundException();
    }
}
