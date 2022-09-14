package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.OrderProduct;
import org.springframework.data.repository.CrudRepository;


public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
    void deleteAllByOrderId(Long orderId);
}
