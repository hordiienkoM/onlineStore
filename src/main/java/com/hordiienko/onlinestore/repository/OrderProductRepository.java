package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {

    Set<Product> findProductsByOrderId(Long orderId);
}
