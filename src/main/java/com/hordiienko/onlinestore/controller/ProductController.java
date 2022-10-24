package com.hordiienko.onlinestore.controller;


import com.hordiienko.onlinestore.dto.ProductGetDTO;
import com.hordiienko.onlinestore.dto.ProductPutDTO;
import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.mapper.ProductMapper;
import com.hordiienko.onlinestore.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Locale;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @ApiOperation("Get products page")
    public ResponseEntity getProductsPage(Pageable pageable) {
        Page<Product> pageProducts = productService.getProducts(pageable);
        return ResponseEntity.ok().body(pageProducts.map(productMapper::toProductGetDTO));
    }

    @DeleteMapping
    @ApiOperation("Delete the product")
    public String deleteById(@RequestParam Long productId, Locale locale) {
        productService.deleteById(productId, locale);
        return "product was deleted";
    }

    @PostMapping
    @ApiOperation("Create new product")
    public ProductGetDTO createNew(@RequestParam String description, Locale locale) {
        Product newProduct = productService.createNew(description, locale);
        return productMapper.toProductGetDTO(newProduct);
    }

    @PutMapping
    @ApiOperation("Update the product")
    public ProductGetDTO update(@Valid @RequestBody ProductPutDTO product, Locale locale) {
        Product updated = productService.update(product, locale);
        return productMapper.toProductGetDTO(updated);
    }
}
