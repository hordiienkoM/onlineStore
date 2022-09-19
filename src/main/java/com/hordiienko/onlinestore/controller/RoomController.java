package com.hordiienko.onlinestore.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomController {

    @GetMapping("/home")
    public String home(){
        return "home_page";
    }
}
