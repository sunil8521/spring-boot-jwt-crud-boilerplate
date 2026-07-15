package com.CURD.curd_operation.controller;

import com.CURD.curd_operation.dto.AuthResponseDto;
import com.CURD.curd_operation.dto.LoginRequest;
import com.CURD.curd_operation.dto.SignupRequest;
import com.CURD.curd_operation.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> signup(@Valid @RequestBody SignupRequest request) {
        AuthResponseDto response = authService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequest request) {
        AuthResponseDto response = authService.loginUser(request);
        return ResponseEntity.ok(response);
    }
}
