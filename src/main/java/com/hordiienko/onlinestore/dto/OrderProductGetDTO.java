package com.hordiienko.onlinestore.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductGetDTO {
    private ProductGetDTO product;
    private Integer amount;
}
