package com.hordiienko.onlinestore.service;


import com.hordiienko.onlinestore.dto.ProductPutDTO;
import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.exception.ProductAlreadyExistException;
import com.hordiienko.onlinestore.exception.ProductNotFoundException;
import com.hordiienko.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void deleteById(Long productId, Locale locale) {
        try{
            productRepository.deleteById(productId);
        } catch (Exception e) {
            throw new ProductNotFoundException(locale);
        }
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(
                pageable
        );
    }

    public Product createNew(String description, Locale locale) {
        if (productRepository.existsByDescription(description)) {
            throw new ProductAlreadyExistException(locale);
        }
        Product product = new Product();
        product.setDescription(description);
        return productRepository.save(product);
    }

    public Product update(ProductPutDTO newProduct, Locale locale) {
        Product product;
        try {
            product = productRepository.findById(newProduct.getId()).orElseThrow();
        } catch (Exception e) {
            throw new ProductNotFoundException(locale);
        }
        product.setDescription(newProduct.getDescription());
        return productRepository.save(product);
    }
}
