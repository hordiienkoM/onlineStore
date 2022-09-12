package com.hordiienko.onlinestore.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductGetDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("productAmount")
    private Integer productAmount;

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("productId")
    private Long productId;
}
