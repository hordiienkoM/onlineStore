package com.hordiienko.onlinestore.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductGetDTO {
    private Long productId;
    private String description;
    private Integer amount;
}
