package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/v1/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    @ApiOperation("Download set of products to database")
    public String downloadProductsToDB(Locale locale) {
        productService.downloadProductsToDB(locale);
        return "Products have been uploaded successfully";
    }

    @DeleteMapping("/products")
    @ApiOperation("Download set of products to database")
    public String deleteAllProducts() {
        productService.deleteAllProducts();
        return "Products have been deleted";
    }
}
