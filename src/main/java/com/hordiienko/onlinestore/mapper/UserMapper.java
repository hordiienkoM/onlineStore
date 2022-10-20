package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.UserGetDTO;
import com.hordiienko.onlinestore.dto.UserInfoGetDTO;
import com.hordiienko.onlinestore.dto.UserPostDTO;
import com.hordiienko.onlinestore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    UserGetDTO toUserGetDTO(User user);

    User toUser(UserPostDTO userPostDTO);


    @Mapping(expression = "java(user.orders.size())", target = "ordersAmount")
    UserInfoGetDTO toUserInfoGetDTO(User user);

}
