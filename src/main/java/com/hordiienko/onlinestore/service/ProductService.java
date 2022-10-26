package com.hordiienko.onlinestore.service;


import com.hordiienko.onlinestore.dto.ProductPutDTO;
import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.exception.ProductAlreadyExistException;
import com.hordiienko.onlinestore.exception.ProductNotFoundException;
import com.hordiienko.onlinestore.exception.ProductsDownloadException;
import com.hordiienko.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DownloadService downloadService;

    public void deleteById(Long productId, Locale locale) {
        try {
            productRepository.deleteById(productId);
        } catch (Exception e) {
            throw new ProductNotFoundException(locale);
        }
    }

    public Product getProduct(Long productId, Locale locale) {
        try {
            return productRepository.findById(productId).orElseThrow();
        } catch (Exception e) {
            throw new ProductNotFoundException(locale);
        }
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(
                pageable
        );
    }

    public Product createNew(Product product, Locale locale) {
        if (productRepository.existsByDescription(product.getDescription())) {
            throw new ProductAlreadyExistException(locale);
        }
        product.setDateCreate(LocalDateTime.now());
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
        product.setBrand(newProduct.getBrand());
        product.setCategory(newProduct.getCategory());
        product.setPrice(newProduct.getPrice());
        return productRepository.save(product);
    }

    public void downloadProductsToDB(Locale locale) {
        for (int i = 0; i < 6; i++) {
            List<Product> products = downloadService.downloadProducts(locale);
            try {
                productRepository.saveAll(products);
            } catch (ConstraintViolationException e) {
                throw e;
            } catch (Exception e) {
                throw new ProductsDownloadException(locale);
            }
        }
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}
