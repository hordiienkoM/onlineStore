package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    Order findDistinctById(Long id);
}
