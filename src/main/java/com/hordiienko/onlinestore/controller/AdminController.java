package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.test_dto.TestValidationDTO;
import com.hordiienko.onlinestore.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Locale;

@RestController
@RequestMapping("/v1/admin")
@Validated
public class AdminController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Delete user by id", notes = "delete user")
    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUserById(@RequestParam Long id, Locale locale) {
        userService.deleteById(id, locale);
        return ResponseEntity.ok().body("user was deleted");
    }

    @ApiOperation(value = "Get exception", notes = "return exception if id<1")
    @GetMapping("/exception/{id}")
    public String getException(@PathVariable
                               @Min(1) Integer id, Locale locale) {
        return id.toString();
    }

    @ApiIgnore
    @GetMapping("/exception")
    public TestValidationDTO getException(@RequestBody @Valid TestValidationDTO testDTO, Locale locale) {
        return testDTO;
    }
}
