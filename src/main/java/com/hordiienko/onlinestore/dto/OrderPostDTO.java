package com.hordiienko.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPostDTO {
    @Pattern(regexp = "^(new|update)$", message = "this email is not correct")
    private String status;
    private LocalDateTime createDate;
    @Pattern(regexp = "^[a-zA-Z0-9 .,]+$", message = "this address is not correct")
    @NotEmpty(message = "Address must be not empty")
    private String address;
    @NotEmpty(message = "You must add at list one product")
    private Set<OrderProductPostDTO> orderProduct;
}
