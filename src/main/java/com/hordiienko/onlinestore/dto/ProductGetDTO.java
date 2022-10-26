package com.hordiienko.onlinestore.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.hordiienko.onlinestore.entity.enums.Brand;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductGetDTO {
    @JsonProperty("product_id")
    private Long id;
    private String description;
    private Double price;
    private String category;
    private String brand;
}
