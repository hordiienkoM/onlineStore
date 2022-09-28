package com.hordiienko.onlinestore.service;


import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(
                pageable
        );
    }
}
