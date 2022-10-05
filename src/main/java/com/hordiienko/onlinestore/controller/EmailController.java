package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.service.EmailSenderService;
import com.hordiienko.onlinestore.service.util.HtmlReader;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@AllArgsConstructor
@RequestMapping(("/v1/email"))
public class EmailController {

    @Autowired
    private EmailSenderService senderService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity sentMessage(){
        try {
            senderService.sendMessage(
                    "testmikhma@gmail.com",
                    "about something"
            );
            return ResponseEntity.ok().body("was send");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping()
//    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
//    public ResponseEntity sentMessage(){
//        try {
//            senderService.sendSimpleMessage(
//                    "testmikhma@gmail.com",
//                    "something about",
//                    HtmlReader.readWelcomePage()
//            );
//            return ResponseEntity.ok().body("message was send");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("error reading html");
//        }
//    }

}
