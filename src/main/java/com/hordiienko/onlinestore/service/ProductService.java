package com.hordiienko.onlinestore.service;


import com.hordiienko.onlinestore.dto.ProductPutDTO;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.entity.enums.Brand;
import com.hordiienko.onlinestore.entity.enums.Category;
import com.hordiienko.onlinestore.exception.*;
import com.hordiienko.onlinestore.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DownloadService downloadService;

    public void deleteById(String productId, Locale locale) {
        try {
            productRepository.deleteById(productId);
        } catch (Exception e) {
            throw new ProductNotFoundException(locale);
        }
    }

    public Product getProduct(String productId, Locale locale) {
        return productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException(locale));
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
        Product product = productRepository.findById(newProduct.getId())
                .orElseThrow(
                        () -> new ProductNotFoundException(locale)
                );
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

    @Transactional
    public Product getHasMaxPrice(Locale locale) {
        return productRepository.streamAllBy().max(
                Comparator.comparingDouble(Product::getPrice)
        ).orElseThrow(() -> new ProductNotFoundException(locale));
    }

    @Transactional
    public Double getAveragePrice() {
        return Math.round(
                productRepository.streamAllBy().collect(
                        Collectors.averagingDouble(Product::getPrice)) * 100
        ) / 100.0;
    }


    @Transactional
    public Set<Product> get20HasBrand(String brandName, Locale locale) {
        Brand brand;
        try {
            brand = Brand.valueOf(brandName);
        } catch (Exception e) {
            throw new BrandNotFoundException(locale);
        }
        return productRepository.streamAllBy()
                .filter(a -> a.getBrand().equals(brand))
                .limit(20)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<Product> get20MinHasCategory(String categoryName, Locale locale) {
        Category category;
        try {
            category = Category.valueOf(categoryName);
        } catch (Exception e) {
            throw new BrandNotFoundException(locale);
        }
        return productRepository.streamAllBy()
                .filter(a -> a.getCategory().equals(category))
                .sorted((a, b) -> (int) ((b.getPrice() - a.getPrice()) * 100))
                .limit(20)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Double averagePriceFromCategory(String categoryName, Locale locale) {
        Category category;
        try {
            category = Category.valueOf(categoryName);
        } catch (Exception e) {
            throw new CategoryNotFoundException(locale);
        }
        return Math.round(
                productRepository.streamAllBy()
                        .filter(a -> a.getCategory().equals(category))
                        .collect(Collectors.averagingDouble(Product::getPrice))
                        * 100
        ) / 100.0;
    }

    @Transactional
    public Double minPriceFromCategory(String categoryName, Locale locale) {
        Category category;
        try {
            category = Category.valueOf(categoryName);
        } catch (Exception e) {
            throw new CategoryNotFoundException(locale);
        }
        return Math.round(
                productRepository.streamAllBy()
                        .filter(a -> a.getCategory().equals(category))
                        .min(Comparator.comparingDouble(Product::getPrice))
                        .orElseThrow(() -> new ProductNotFoundException(locale))
                        .getPrice()
                        * 100
        ) / 100.0;
    }

    @Transactional
    public Double maxPriceFromBrandInCategory(String categoryName, String brandName, Locale locale) {
        Category category;
        Brand brand;
        try {
            category = Category.valueOf(categoryName);
            brand = Brand.valueOf(brandName);
        } catch (Exception e) {
            throw new CategoryOrBrandNotFoundException(locale);
        }
        return Math.round(
                productRepository.streamAllBy()
                        .filter(a -> a.getCategory().equals(category))
                        .filter(a -> a.getBrand().equals(brand))
                        .min(Comparator.comparingDouble(Product::getPrice))
                        .orElseThrow()
                        .getPrice()
                        * 100
        ) / 100.0;
    }

    @Transactional
    public Map<Category, Map<Brand, List<String>>> getMapStructure() {
        return productRepository.streamAllBy()
                .limit(100)
                .collect(
                        Collectors.groupingBy(
                                Product::getCategory,
                                Collectors.groupingBy(Product::getBrand,
                                        Collectors.mapping(Product::getId, Collectors.toList()))
                        ));
    }

    public Map<Category, Map<Brand, Double>> getCategoryBrandSum() {
        return productRepository.streamAllBy()
                .collect(
                        Collectors.groupingBy(
                                Product::getCategory,
                                Collectors.groupingBy(Product::getBrand,
                                        Collectors.summingDouble(Product::getPrice))
                        ));
    }


    @Transactional
    public Map<Category, Map<Brand, Double>> getCategoryBrandMaxPrice() {
        return productRepository.streamAllBy()
                .collect(
                        Collectors.groupingBy(
                                Product::getCategory,
                                Collectors.groupingBy(
                                        Product::getBrand,
                                        Collectors.collectingAndThen(
                                                Collectors.maxBy(Comparator.comparing(Product::getPrice)),
                                                k -> k.orElseThrow().getPrice()
                                        )
                                )
                        ));
    }

    public Map<String, Product> getProductMap(Order order, Locale locale) {
        Set<String> productIds = order.getOrderProduct().stream()
                .map(OrderProduct::getProductId)
                .collect(Collectors.toSet());
        Set<Product> products;
        try {
            products = productRepository.findByIdIn(productIds);
        } catch (Exception e) {
            throw new ProductNotFoundException(locale);
        }
        return products.stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        product -> product
                ));
    }

    @Transactional
    public Map<User, String> findProductRecommendations(Set<User> users) {
        Map<User, String> productRecommendations = new HashMap<>();
        for (User user : users) {
            Product purchasedProduct = randomProductFromLastOrder(user, user.getLocale());
            Set<Product> similarProducts = findThreeSimilarProducts(purchasedProduct);
            String htmlProductsInfo = getProductsInfo(similarProducts);
            productRecommendations.put(user, htmlProductsInfo);
        }
        return productRecommendations;
    }

    Product randomProductFromLastOrder(User user, Locale locale) {
        Set<Order> orders = user.getOrders();
        String randomProductId = orders.stream()
                .max((o1, o2) -> o1.getCreateDate().compareTo(o2.getCreateDate()))
                .map(Order::getOrderProduct)
                .map(Set::stream)
                .flatMap(Stream::findFirst)
                .map(OrderProduct::getProductId)
                .orElseThrow();
        return productRepository.findById(randomProductId)
                .orElseThrow(() -> new ProductNotFoundException(locale));
    }

    private Set<Product> findThreeSimilarProducts(Product product) {
        return productRepository.streamAllByBrand(product.getBrand())
                .limit(3)
                .collect(Collectors.toSet());
    }

    private String getProductsInfo(Set<Product> products) {
        StringBuilder productsInfo = new StringBuilder();
        products.forEach(e -> {
            productsInfo.append(e.toString())
                    .append("; \n");
        });
        return productsInfo.toString();
    }
}
