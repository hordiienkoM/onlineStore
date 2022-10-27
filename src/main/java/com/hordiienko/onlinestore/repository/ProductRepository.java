package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;


public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByDescription(String description);

    Stream<Product> streamAllBy();
}
