package com.hordiienko.onlinestore.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductGetDTO {

    private Long id;
    private Integer amount;
    private OrderGetDTO order;
    private ProductGetDTO product;
}
