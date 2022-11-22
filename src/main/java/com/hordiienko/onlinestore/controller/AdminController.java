package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.entity.document.ProductDoc;
import com.hordiienko.onlinestore.service.ProductSearchService;
import com.hordiienko.onlinestore.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Set;

@RestController
@RequestMapping("/v1/admin")
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSearchService productSearchService;

    @PostMapping("/products")
    @ApiOperation("Download set of products to database")
    public String downloadProductsToDB(Locale locale) {
        productService.downloadProductsToDB(locale);
        return "Products have been uploaded successfully";
    }

    @DeleteMapping("/products")
    @ApiOperation("Delete all products from database")
    public String deleteAllProducts() {
        productService.deleteAllProducts();
        return "Products have been deleted";
    }

    @PostMapping("/products_doc")
    @ApiOperation("Add all products to elasticsearch db like 'ProductDoc'")
    public HttpStatus createProductsDoc() {
        productSearchService.createAllProductIndices();
        return HttpStatus.OK;
    }

    @GetMapping("/products_doc")
    @ApiOperation("Get all 'ProductDocs'")
    public Set<ProductDoc> getAllProductsDoc() {
        return productSearchService.getAllProducts();
    }

    @DeleteMapping("/products_doc")
    @ApiOperation("Delete all productDocs")
    public HttpStatus deleteAllProductsDoc() {
        productSearchService.deleteAll();
        return HttpStatus.OK;
    }

}
