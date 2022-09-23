package com.hordiienko.onlinestore.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGetInfoDTO {
    @JsonIgnore
    private Long id;
    private String status;
    private LocalDateTime createDate;
    private String address;
}
