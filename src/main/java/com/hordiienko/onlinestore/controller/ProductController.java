package com.hordiienko.onlinestore.controller;


import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.mapper.ProductMapper;
import com.hordiienko.onlinestore.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private ResponseEntity getProductsPage(@RequestParam Integer page,
                                           @RequestParam Integer pageSize,
                                           @RequestParam String sortField){
        Page<Product> pageProductsDTO = productService.getProducts(page, pageSize, sortField);
        return ResponseEntity.ok().body(productMapper.toProductGetDTOs(pageProductsDTO));
    }
}
