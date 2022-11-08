package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.OrderProductGetDTO;
import com.hordiienko.onlinestore.dto.OrderProductInfoGetDTO;
import com.hordiienko.onlinestore.dto.OrderProductPostDTO;
import com.hordiienko.onlinestore.entity.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderProductMapper {
    @Mapping(target = "description", ignore = true)
    OrderProductGetDTO toOrderProductGetDTO(OrderProduct orderProduct);

    @Mapping(target = "description", ignore = true)
    OrderProductInfoGetDTO toOrderProductInfoGetDTO(OrderProduct orderProduct);

    OrderProduct toOrderProduct(OrderProductPostDTO orderProductPostDTO);
}
