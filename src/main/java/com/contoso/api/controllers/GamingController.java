package com.contoso.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GamingController {

    @GetMapping("/welcome")
    public ResponseEntity<String> getWelcomeMessage() {
        return new ResponseEntity<>("Welcome to Contoso Gaming Platform", HttpStatus.OK);
    }

}
