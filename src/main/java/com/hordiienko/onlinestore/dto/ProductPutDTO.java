package com.hordiienko.onlinestore.dto;

import com.hordiienko.onlinestore.entity.enums.Brand;
import com.hordiienko.onlinestore.entity.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPutDTO {
    private Long id;
    @NotEmpty
    private String description;
    @DecimalMin("0.0")
    private Double price;
    private Brand brand;
    private Category category;
}
