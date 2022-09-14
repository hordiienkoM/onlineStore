package com.hordiienko.onlinestore.service;


import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(Long productId){
        return productRepository.findById(productId).get();
    }
}
