package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.OrderProductGetDTO;
import com.hordiienko.onlinestore.dto.OrderProductPostDTO;
import com.hordiienko.onlinestore.entity.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper
public interface OrderProductMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.description", target = "description")
    OrderProductGetDTO toOrderProductGetDTO(OrderProduct orderProduct);

    OrderProduct toOrderProduct(OrderProductPostDTO orderProductPostDTO);

    Set<OrderProductGetDTO> toOrderProductGetDTOs(Set<OrderProduct> products);
}
