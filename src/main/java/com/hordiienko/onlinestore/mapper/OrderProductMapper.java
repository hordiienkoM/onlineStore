package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.OrderProductGetDTO;
import com.hordiienko.onlinestore.dto.OrderProductPostDTO;
import com.hordiienko.onlinestore.entity.OrderProduct;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
public interface OrderProductMapper {
    OrderProductGetDTO toOrderProductGetDTO(OrderProduct orderProduct);

    OrderProduct toOrderProduct(OrderProductPostDTO orderProductPostDTO);

    Set<OrderProductGetDTO> toOrderProductGetDTOs(Set<OrderProduct> orderProducts);
}
