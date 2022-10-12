package com.hordiienko.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class UserConfirmDTO {
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "this username isn't correct")
    private String username;
    @Pattern(regexp = "^[0-9]{6}$", message = "this code isn't correct")
    private String token;
}
