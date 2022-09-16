package com.hordiienko.onlinestore.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductGetDTO {
    private Long id;
    private String description;
}
