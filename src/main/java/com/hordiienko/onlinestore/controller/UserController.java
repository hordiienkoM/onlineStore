package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.entity.Order;
import com.hordiienko.onlinestore.entity.User;
import com.hordiienko.onlinestore.exception.UserAlreadyExistException;
import com.hordiienko.onlinestore.repository.UserRepository;
import com.hordiienko.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity registration (@RequestBody User user){
        try {
            userService.registration(user);
            return ResponseEntity.ok("User saved successfully");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping("/orders")
//    @ResponseBody
//    public  ResponseEntity getOrders (@RequestBody Long id){
//    public  ResponseEntity getOrders (@RequestParam Long id){
//        try {
//            return ResponseEntity.ok().body(id);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Find orders error");
//        }
//    }

    @PostMapping("/testConnect")
    public ResponseEntity getOrders() {
        try {
            return ResponseEntity.ok("Server works");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Server error");
        }
    }
}
