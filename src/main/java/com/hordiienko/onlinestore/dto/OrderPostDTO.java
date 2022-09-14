package com.hordiienko.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPostDTO {
    private UserPostDTO user;
    private String status;
    private LocalDateTime createDate;
    private String address;
    private Set<OrderProductPostDTO> orderProduct;
}
