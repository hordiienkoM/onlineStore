package com.hordiienko.onlinestore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetDTO {
    private Long id;
    private String description;
}
