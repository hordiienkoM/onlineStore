package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.dto.UserPostDTO;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.mapper.UserMapper;
import com.hordiienko.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/v1/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/confirm")
    public ResponseEntity confirmRegistration(
            @RequestParam("username") @Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "this username isn't correct")
            String username,
            @RequestParam("token") @Pattern(regexp = "^[0-9]{6}$", message = "this code isn't correct")
            String token
    ) {
        try{
            userService.confirmRegistration(username, token);
            return ResponseEntity.ok().body("registration completed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity registrationUser(@Valid @RequestBody UserPostDTO newUser){
        try {
            User user = userMapper.toUser(newUser);
            userService.registrationUser(user);
            return ResponseEntity.ok().body("User was saved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //    the method to delete
    @DeleteMapping()
    public  ResponseEntity deleteById(@RequestParam Long id){
        try {
            userService.deleteById(id);
            return ResponseEntity.ok().body("user was deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
