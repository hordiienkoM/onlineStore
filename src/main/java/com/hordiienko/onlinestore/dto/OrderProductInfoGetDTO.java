package com.hordiienko.onlinestore.dto;

import com.hordiienko.onlinestore.dto.parent_interface.OrderProductInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductInfoGetDTO implements OrderProductInfo {
    private String description;
    private Integer amount;
}
