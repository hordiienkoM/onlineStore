package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.ProductGetDTO;
import com.hordiienko.onlinestore.dto.ProductInnerDTO;
import com.hordiienko.onlinestore.entity.Product;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
public interface ProductMapper {
    Set<ProductGetDTO> toProductGetDTOs(Set<Product> products);
    ProductGetDTO toProductGetDTO(Product product);
    Product toProduct(ProductInnerDTO innerProductDTO);
}
