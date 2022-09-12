package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.OrderGetDTO;
import com.hordiienko.onlinestore.dto.OrderProductGetDTO;
import com.hordiienko.onlinestore.dto.ProductGetDTO;
import com.hordiienko.onlinestore.dto.UserGetDTO;
import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.OrderProduct;
import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper (componentModel = "spring")
public interface MapstructMapper {
    MapstructMapper INSTANCE = Mappers.getMapper(MapstructMapper.class);
    OrderGetDTO orderToOrderGetDTO(Order order);
    UserGetDTO userToUserGetDTO(User user);
    OrderProductGetDTO toOrderProductGetDTO(OrderProduct orderProduct);
    ProductGetDTO toOrderProductGetDTO(Product product);
    Set<OrderGetDTO> toOrderGetDTOs(Set<Order> orders);
    Set<UserGetDTO> toUserGetDTOs(Set<User> users);
}

