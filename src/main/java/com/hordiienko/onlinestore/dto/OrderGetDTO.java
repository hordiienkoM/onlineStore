package com.hordiienko.onlinestore.dto;


import com.hordiienko.onlinestore.entity.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGetDTO {
    private Long id;
    private Status status;
    private LocalDateTime createDate;
    private String address;
}
