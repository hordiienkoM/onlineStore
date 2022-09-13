package com.hordiienko.onlinestore.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductGetDTO {
    private ProductGetDTO product;
    private Integer amount;
}
