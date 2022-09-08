package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.GregorianCalendar;
import java.util.Set;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Set<Order> getOrders(Long userId)throws OrderNotFoundException {
        try {
            return orderRepository.findAllByUserId(userId);
        } catch (Exception e) {
            throw  new OrderNotFoundException();
        }
    }

    public GregorianCalendar getDate (Long id) throws OrderNotFoundException {
        try {
            return orderRepository.findById(id).get().getCreateDate();
        } catch (Exception e) {
            throw  new OrderNotFoundException();
        }
    }

    public String getStatus (Long id) throws OrderNotFoundException {
        try {
            return orderRepository.findById(id).get().getStatus();
        } catch (Exception e) {
            throw  new OrderNotFoundException();
        }
    }
}
