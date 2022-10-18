package com.hordiienko.onlinestore.dto.test_dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Data
public class TestValidationDTO {
    @Min(value = 1, message = "{error.test_error}")
    Integer num;
}
