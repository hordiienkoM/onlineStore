package com.hordiienko.onlinestore.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductGetDTO {
    @JsonProperty("product_id")
    private String id;
    private String description;
    private Double price;
    private String category;
    private String brand;
}
