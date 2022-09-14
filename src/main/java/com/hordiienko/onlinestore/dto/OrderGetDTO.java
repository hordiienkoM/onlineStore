package com.hordiienko.onlinestore.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGetDTO {
    private Long id;
    private String status;
    private LocalDateTime createDate;
    private String address;
    private Set<OrderProductGetDTO> orderProduct;
}
