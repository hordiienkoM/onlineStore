package com.hordiienko.onlinestore.dto;


import com.hordiienko.onlinestore.dto.parent_interface.OrderProductInfo;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductGetDTO implements OrderProductInfo {
    private String productId;
    private String description;
    private Integer amount;
}
