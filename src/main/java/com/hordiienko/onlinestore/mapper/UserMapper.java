package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.UserGetDTO;
import com.hordiienko.onlinestore.dto.UserPostDTO;
import com.hordiienko.onlinestore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    UserGetDTO toUserGetDTO(User user);

    @Mapping(source = "userId", target = "id")
    User toUser(UserPostDTO userPostDTO);
}
