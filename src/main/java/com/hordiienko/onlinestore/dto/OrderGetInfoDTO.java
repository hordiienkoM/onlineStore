package com.hordiienko.onlinestore.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hordiienko.onlinestore.entity.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGetInfoDTO {
    @JsonIgnore
    private Long id;
    private Status status;
    private LocalDateTime createDate;
    private String address;
}
