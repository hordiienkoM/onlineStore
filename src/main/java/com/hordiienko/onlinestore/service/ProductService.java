package com.hordiienko.onlinestore.service;


import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    public Page<Product> getProducts(Integer page, Integer size, String sortField) {
        return productRepository.findAll(
                PageRequest.of(page, size).withSort(Sort.by(sortField))
        );
    }
}
