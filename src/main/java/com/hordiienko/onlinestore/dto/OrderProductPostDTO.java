package com.hordiienko.onlinestore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductPostDTO {
    private Integer amount;
    private ProductInnerDTO product;
}
