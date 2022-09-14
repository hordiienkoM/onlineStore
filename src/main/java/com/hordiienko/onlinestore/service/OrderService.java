package com.hordiienko.onlinestore.service;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.OrderNotFoundException;
import com.hordiienko.onlinestore.exception.OrderSaveException;
import com.hordiienko.onlinestore.repository.OrderProductRepository;
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

    public void saveOrder(Order order) throws OrderSaveException{
        try {
            orderRepository.save(order);
        } catch (Exception e){
            throw new OrderSaveException();
        }
    }

    public void deleteOrder(Long orderId) throws OrderNotFoundException{
        try {
            orderRepository.deleteById(orderId);
        } catch (Exception e){
            throw new OrderNotFoundException();
        }
    }
}
