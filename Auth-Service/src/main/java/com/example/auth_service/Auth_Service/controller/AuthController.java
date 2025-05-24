package com.example.auth_service.Auth_Service.controller;

import com.example.auth_service.Auth_Service.dto.SignUp;
import com.example.auth_service.Auth_Service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<?> createSignUp(@RequestBody SignUp signUp){
        return authService.createSignUp(signUp);
    }
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody SignUp signUp){
        return authService.signIn(signUp);
    }

}
