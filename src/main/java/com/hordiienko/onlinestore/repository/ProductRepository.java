package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    boolean existsByDescription(String description);
}
