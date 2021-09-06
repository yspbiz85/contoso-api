package com.contoso.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
    @GetMapping("/welcome")
    public ResponseEntity<Boolean> welcome() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
