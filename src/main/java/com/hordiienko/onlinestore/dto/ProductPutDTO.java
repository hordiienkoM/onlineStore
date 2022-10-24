package com.hordiienko.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPutDTO {
    private Long id;
    @NotEmpty
    private String description;
}
