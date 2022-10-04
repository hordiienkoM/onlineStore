package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(("/v1/email"))
public class EmailController {

    @Autowired
    private EmailSenderService senderService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity sentMessage(){
        senderService.sendSimpleMessage(
                "mikhail.gordiyenko@faceit-team.com",
                "something about",
                "text about something very important"
        );
        return ResponseEntity.ok().body("message was send");
    }

}
