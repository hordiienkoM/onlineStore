package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.ProductGetDTO;
import com.hordiienko.onlinestore.dto.ProductInnerDTO;
import com.hordiienko.onlinestore.entity.Product;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.Set;
import java.util.function.Function;

@Mapper
public interface ProductMapper {
//    Page<ProductGetDTO> toProductGetDTOs(Page<Product> products);
    ProductGetDTO toProductGetDTO(Product product);
    Product toProduct(ProductInnerDTO innerProductDTO);
    default Page<ProductGetDTO> toProductGetDTOs(Page<Product> products){
        return products.map(new Function<Product, ProductGetDTO>() {
            @Override
            public ProductGetDTO apply(Product product) {
                return toProductGetDTO(product);
            }
        });
    }
}
