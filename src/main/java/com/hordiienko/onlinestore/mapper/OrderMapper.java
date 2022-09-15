package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.OrderGetDTO;
import com.hordiienko.onlinestore.dto.OrderInnerDTO;
import com.hordiienko.onlinestore.dto.OrderPostDTO;
import com.hordiienko.onlinestore.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper
public interface OrderMapper {
    Set<OrderGetDTO> toOrderGetDTOs(Set<Order> orders);

    @Mapping(source = "createDate", target = "createDate", dateFormat = "yyyy-MM-dd hh:mm:ss")
    Order postDtoToOrder(OrderPostDTO orderPostDTO);

    OrderGetDTO toOrderGetDTO(Order order);
    Order innerOrderToOrder(OrderInnerDTO innerOrder);


}
