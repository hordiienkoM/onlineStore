package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.entity.document.ProductDoc;
import org.mapstruct.Mapper;

@Mapper
public interface ProductDocMapper {
    ProductDoc toProductDoc(Product product);
}
