package com.hordiienko.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class UserConfirmDTO {
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "{registration.uncorrected.email}")
    private String username;
    @Pattern(regexp = "^[0-9]{6}$", message = "{message.uncorrected_token}")
    private String token;
}
