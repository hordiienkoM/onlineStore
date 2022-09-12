package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.OrderGetDTO;
import com.hordiienko.onlinestore.entity.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {
    OrderGetDTO toDTO (Order order);
}
