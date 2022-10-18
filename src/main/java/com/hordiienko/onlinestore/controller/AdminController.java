package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.test_dto.TestValidationDTO;
import com.hordiienko.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/v1/admin")
@Validated
public class AdminController {

    @Autowired
    private UserService userService;

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUserById(@RequestParam Long id, Locale locale) {
        userService.deleteById(id, locale);
        return ResponseEntity.ok().body("user was deleted");
    }

    @GetMapping("/exception/{id}")
    public String getException(@PathVariable
                                   @Min(value = 1, message = "{error.less_min}") Integer id, Locale locale){
        return id.toString();
    }

    @GetMapping("/exception")
    public TestValidationDTO getException(@RequestBody @Valid TestValidationDTO testDTO, Locale locale){
        return testDTO;
    }
}
