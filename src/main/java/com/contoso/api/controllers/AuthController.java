package com.contoso.api.controllers;

import com.contoso.api.model.LoginRequest;
import com.contoso.api.model.SignupRequest;
import com.contoso.api.model.UserResponse;
import com.contoso.api.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    UserAuthService userAuthService;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signup(@RequestBody @Valid SignupRequest signupRequest) {
        return new ResponseEntity<>(this.userAuthService.signup(signupRequest), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid  LoginRequest loginRequest) {
        return new ResponseEntity<>(this.userAuthService.login(loginRequest), HttpStatus.OK);
    }
}
