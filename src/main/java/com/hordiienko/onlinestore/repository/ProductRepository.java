package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
//    Set<Product> findAllByOrderProductContainingOrderById(Long orderId);
//    Set<Product> findAllByOrderProductContainingOrderById(Long orderId);
}
