package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.*;
import com.hordiienko.onlinestore.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper
public interface ProductMapper {
    @Mapping(expression = "java(product.getCategory().getName())", target = "category")
    @Mapping(expression = "java(product.getBrand().getName())", target = "brand")
    ProductGetDTO toProductGetDTO(Product product);

    Product toProduct(ProductPostDTO productPostDTO);

    Set<ProductGetDTO> toProductGetDTOs(Set<Product> products);
}
