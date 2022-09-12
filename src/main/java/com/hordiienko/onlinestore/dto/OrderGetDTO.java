package com.hordiienko.onlinestore.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.GregorianCalendar;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGetDTO {
    private Long id;
    private String status;
    private GregorianCalendar createDate;
    private String address;
    private Set<OrderProductGetDTO> orderProduct;
}
