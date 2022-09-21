package com.hordiienko.onlinestore.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGetDTO {
    private Long id;
    private String status;
    private LocalDateTime createDate;
    private String address;
}
