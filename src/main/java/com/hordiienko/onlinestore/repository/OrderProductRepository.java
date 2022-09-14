package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.OrderProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;


public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
    Set<OrderProduct> findAllByOrderId(Long orderId);

    void deleteAllByOrderId(Long orderId);
}
