package com.hordiienko.onlinestore.mapper;

import com.hordiienko.onlinestore.dto.UserGetDTO;
import com.hordiienko.onlinestore.dto.UserPostDTO;
import com.hordiienko.onlinestore.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserGetDTO toUserGetDTO(User user);

    default User toUser(UserPostDTO userPostDTO){
        User user = new User();
        user.setUsername(userPostDTO.getUsername());
        user.setPassword(userPostDTO.getPassword());
        return user;
    }

}
