package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.exception.ProductNotFoundException;
import com.hordiienko.onlinestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("/description")
    public ResponseEntity getDescription(Long productId){
        try{
            return ResponseEntity.ok().body(productService.getDescription(productId));
        } catch (ProductNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
