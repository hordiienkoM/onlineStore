package com.hordiienko.onlinestore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPostDTO {
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "this email is not correct")
//    @Email(message = "this email is not correct")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9_-]{5,15}$", message = "this password is not correct")
    private String password;
}
