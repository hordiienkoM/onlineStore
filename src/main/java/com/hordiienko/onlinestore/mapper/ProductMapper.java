package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.ProductGetDTO;
import com.hordiienko.onlinestore.dto.ProductInnerDTO;
import com.hordiienko.onlinestore.entity.Product;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.function.Function;

@Mapper
public interface ProductMapper {
    ProductGetDTO toProductGetDTO(Product product);

    Product toProduct(ProductInnerDTO innerProductDTO);
}
