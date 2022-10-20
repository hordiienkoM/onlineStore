package com.hordiienko.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoGetDTO {
    private Long id;
    private String username;
    private Integer ordersAmount;
}
