package com.hordiienko.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderPostDTO {
    private LocalDateTime createDate;
    @Pattern(regexp = "^[a-zA-Z0-9 .,]+$", message = "this address is not correct")
    @NotEmpty
    private String address;
    @NotEmpty
    private Set<OrderProductPostDTO> orderProduct;
}
