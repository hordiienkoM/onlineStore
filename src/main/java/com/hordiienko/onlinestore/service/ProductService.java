package com.hordiienko.onlinestore.service;


import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.exception.ProductNotFoundException;
import com.hordiienko.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String getDescription(Long productId) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            return product.get().getDescription();
        }
        throw new ProductNotFoundException();
    }
}
