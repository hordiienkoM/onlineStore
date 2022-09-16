package com.hordiienko.onlinestore.repository;

import com.hordiienko.onlinestore.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
