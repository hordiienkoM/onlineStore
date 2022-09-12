package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.ProductGetDTO;
import com.hordiienko.onlinestore.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    ProductGetDTO toDTO(Product product);
}
