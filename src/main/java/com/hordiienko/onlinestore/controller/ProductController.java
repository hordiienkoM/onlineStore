package com.hordiienko.onlinestore.controller;


import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.mapper.ProductMapper;
import com.hordiienko.onlinestore.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public ResponseEntity getProductsPage(Pageable pageable){
        Page<Product> pageProductsDTO = productService.getProducts(pageable);
        return ResponseEntity.ok().body(productMapper.toProductGetDTOs(pageProductsDTO));
    }
}
