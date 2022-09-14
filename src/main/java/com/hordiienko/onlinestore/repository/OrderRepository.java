package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.Order;
import org.springframework.data.repository.CrudRepository;


public interface OrderRepository extends CrudRepository<Order, Long> {
}
