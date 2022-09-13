package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.OrderGetDTO;
import com.hordiienko.onlinestore.dto.OrderPostDTO;
import com.hordiienko.onlinestore.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper
public interface OrderMapper {
    Set<OrderGetDTO> toOrderGetDTOs(Set<Order> orders);

    Order toOrder(OrderPostDTO orderPostDTO);
}
