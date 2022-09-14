package com.hordiienko.onlinestore.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetDTO {
    private Long id;
    private String description;
}
