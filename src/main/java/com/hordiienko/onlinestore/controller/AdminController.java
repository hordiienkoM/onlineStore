package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.test_dto.TestValidationDTO;
import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.mapper.UserMapper;
import com.hordiienko.onlinestore.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private UserMapper userMapper;
    @ApiOperation(value = "Delete user by id", notes = "delete user")
    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUserById(@RequestParam Long id, Locale locale) {
        userService.deleteById(id, locale);
        return ResponseEntity.ok().body("user was deleted");
    }

    @GetMapping("/users")
    @ApiOperation("Get users page")
    public ResponseEntity getUsersPage(Pageable pageable) {
        Page<User> pageUsers = userService.getUsers(pageable);
        return ResponseEntity.ok().body(pageUsers.map(userMapper::toUserInfoGetDTO));
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
