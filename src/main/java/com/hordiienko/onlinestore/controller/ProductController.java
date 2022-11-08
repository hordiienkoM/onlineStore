package com.hordiienko.onlinestore.controller;


import com.hordiienko.onlinestore.dto.ProductGetDTO;
import com.hordiienko.onlinestore.dto.ProductPostDTO;
import com.hordiienko.onlinestore.dto.ProductPutDTO;
import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.entity.enums.Brand;
import com.hordiienko.onlinestore.entity.enums.Category;
import com.hordiienko.onlinestore.mapper.ProductMapper;
import com.hordiienko.onlinestore.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    @ApiOperation("Get products page")
    public ResponseEntity getProductsPage(Pageable pageable) {
        Page<Product> pageProducts = productService.getProducts(pageable);
        return ResponseEntity.ok().body(pageProducts.map(productMapper::toProductGetDTO));
    }

    @GetMapping("/info")
    @ApiOperation("Get product info")
    public ResponseEntity getProductInfo(@RequestParam String id, Locale locale) {
        Product product = productService.getProduct(id, locale);
        return ResponseEntity.ok().body(productMapper.toProductGetDTO(product));
    }

    @DeleteMapping
    @ApiOperation("Delete the product")
    public String deleteById(@RequestParam String productId, Locale locale) {
        productService.deleteById(productId, locale);
        return "product was deleted";
    }

    @PostMapping
    @ApiOperation("Create new product")
    public ProductGetDTO createNew(@RequestBody @Valid ProductPostDTO productPostDTO, Locale locale) {
        Product newProduct = productMapper.toProduct(productPostDTO);
        Product createdProduct = productService.createNew(newProduct, locale);
        return productMapper.toProductGetDTO(createdProduct);
    }

    @PutMapping
    @ApiOperation("Update the product")
    public ProductGetDTO update(@Valid @RequestBody ProductPutDTO product, Locale locale) {
        Product updated = productService.update(product, locale);
        return productMapper.toProductGetDTO(updated);
    }

    @GetMapping("/has_max_price")
    @ApiOperation("Return the product has max price")
    public ProductGetDTO getHasMaxPrice() {
        Product product = productService.getHasMaxPrice();
        return productMapper.toProductGetDTO(product);
    }

    @GetMapping("/average_price")
    @ApiOperation("Return average price from all products")
    public Double getAveragePrice() {
        return productService.getAveragePrice();
    }

    @GetMapping("/has_brands")
    @ApiOperation("Return 20 first products, that have the brand from all products")
    public Set<ProductGetDTO> get20HasBrand(@RequestParam String brandName, Locale locale) {
        Set<Product> products = productService.get20HasBrand(brandName, locale);
        return productMapper.toProductGetDTOs(products);
    }

    @GetMapping("/has_category")
    @ApiOperation("Return 20 first products, that have the category and with the lowest cost from all products")
    public Set<ProductGetDTO> get20MinHasCategory(@RequestParam String categoryName, Locale locale) {
        Set<Product> products = productService.get20MinHasCategory(categoryName, locale);
        return productMapper.toProductGetDTOs(products);
    }

    @GetMapping("/average_price_from_category")
    @ApiOperation("Return average price from a category")
    public Double getAveragePriceFromCategory(@RequestParam String categoryName, Locale locale) {
        return productService.averagePriceFromCategory(categoryName, locale);
    }

    @GetMapping("/min_price_from_category")
    @ApiOperation("Return min price from a category")
    public Double minPriceFromCategory(@RequestParam String categoryName, Locale locale) {
        return productService.minPriceFromCategory(categoryName, locale);
    }

    @GetMapping("/max_price_from_brand_in_category")
    @ApiOperation("Return max price from brand in specific category")
    public Double maxPriceFromBrandInCategory(@RequestParam String categoryName, String brand, Locale locale) {
        return productService.maxPriceFromBrandInCategory(categoryName, brand, locale);
    }

    @GetMapping("/products_structure")
    @ApiOperation("Return structure products like Map<Category, Map<Brand, List<Long>>>")
    public Map<Category, Map<Brand, List<String>>> getProductsStructure() {
        return productService.getMapStructure();
    }

    @GetMapping("/category_brand_sum")
    @ApiOperation("Return structure products like Map<Category, Map<Brand, Double>>")
    public Map<Category, Map<Brand, Double>> getCategoryBrandSum() {
        return productService.getCategoryBrandSum();
    }

    @GetMapping("/category_brand_max-price")
    @ApiOperation("Return structure products like Map<Category, Map<Brand, Double>>")
    public Map<Category, Map<Brand, Double>> getCategoryBrandMaxPrice() {
        return productService.getCategoryBrandMaxPrice();
    }

}
