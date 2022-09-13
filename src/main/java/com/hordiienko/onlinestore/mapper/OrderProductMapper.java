package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.OrderProductGetDTO;
import com.hordiienko.onlinestore.entity.OrderProduct;
import org.mapstruct.Mapper;

@Mapper
public interface OrderProductMapper {
    OrderProductGetDTO toOrderProductGetDTO(OrderProduct orderProduct);

    OrderProduct toOrderProduct(OrderProductGetDTO orderProductGetDTO);
}
