package com.hordiienko.onlinestore.model;

import com.hordiienko.onlinestore.entity.Product;

import java.util.List;

public class Descriptor {

    public String getDescription(List<Product> products){
        StringBuilder builder = new StringBuilder();
        for(Product product: products){
            builder.append(descriptionProduct(product));
        }
        return builder.toString();
    }

    private String descriptionProduct(Product product) {
        return "product id: " + product.getId() + ": " + product.getDescription() + "; \n";
    }
}
