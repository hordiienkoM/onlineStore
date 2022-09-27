package com.hordiienko.onlinestore.dto;


import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDTO {
    private Long id;
    private String username;
    private Set<OrderGetDTO> orders;
}
