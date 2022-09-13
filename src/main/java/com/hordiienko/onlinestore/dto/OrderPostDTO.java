package com.hordiienko.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.GregorianCalendar;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPostDTO {
    private UserPostDTO user;
    private String status;
    private GregorianCalendar createDate;
    private String address;
//    private Set<OrderProductGetDTO> products;
}
