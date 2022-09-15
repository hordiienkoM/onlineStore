package com.hordiienko.onlinestore.controller;


import com.hordiienko.onlinestore.dto.ProductGetDTO;
import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.mapper.ProductMapper;
import com.hordiienko.onlinestore.repository.ProductRepository;
import com.hordiienko.onlinestore.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    private ResponseEntity getAllProducts(){
        Set<Product> products = productService.getAllProduct();
        Set<ProductGetDTO> productsDTO = productMapper.toProductGetDTOs(products);
        return ResponseEntity.ok().body(productsDTO);
    }
}
