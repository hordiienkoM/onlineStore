package com.hordiienko.onlinestore.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDTO {
    private Long id;
    private String name;
    private Set<OrderGetDTO> orders;
}
