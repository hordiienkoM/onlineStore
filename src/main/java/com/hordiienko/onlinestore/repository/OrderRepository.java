package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Set<Order> findAllByUserId(Long userId);

}
