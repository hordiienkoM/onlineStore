package com.hordiienko.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductPostDTO {
    @NotEmpty
    private String description;
    @Pattern(regexp = "^[$]\\d+[.]\\d{2}( - [$]\\d+[.]\\d{2})?$", message = "{error.price.not_correct}")
    private String price;
}
