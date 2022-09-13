package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.OrderGetDTO;
import com.hordiienko.onlinestore.entity.Order;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
public interface OrderMapper {
    Set<OrderGetDTO> toOrderGetDTOs(Set<Order> orders);
}
