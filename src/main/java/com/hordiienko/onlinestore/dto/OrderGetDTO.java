package com.hordiienko.onlinestore.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.GregorianCalendar;
import java.util.Set;

@Getter
@Setter
public class OrderGetDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("createDate")
    private GregorianCalendar createDate;

    @JsonProperty("address")
    private String address;

    @JsonProperty("user")
    private UserGetDTO user;

    @JsonProperty("orderProduct")
    private Set<OrderProductGetDTO> orderProducts;
}
