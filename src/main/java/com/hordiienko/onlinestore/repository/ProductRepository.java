package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
