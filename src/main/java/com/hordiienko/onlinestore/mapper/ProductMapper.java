package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.ProductGetDTO;
import com.hordiienko.onlinestore.entity.Product;

public interface ProductMapper {
    ProductGetDTO toProductGetDTO(Product product);
}
