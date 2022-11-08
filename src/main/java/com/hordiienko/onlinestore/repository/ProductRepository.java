package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;
import java.util.stream.Stream;


public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsByDescription(String description);

    Stream<Product> streamAllBy();

    Set<Product> findByIdIn(Set<String> ids);
}
