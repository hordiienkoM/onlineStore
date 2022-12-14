package com.hordiienko.onlinestore.controller;

import com.hordiienko.onlinestore.service.SessionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @ApiOperation("return the id of the authorized user")
    public ResponseEntity getCurrentUserId(Authentication authentication) {
        return ResponseEntity.ok().body(sessionService.getCurrentUserId(authentication));
    }
}
