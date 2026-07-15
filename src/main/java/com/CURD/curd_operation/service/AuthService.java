package com.CURD.curd_operation.service;

import com.CURD.curd_operation.dto.AuthResponseDto;
import com.CURD.curd_operation.dto.LoginRequest;
import com.CURD.curd_operation.dto.SignupRequest;
import com.CURD.curd_operation.entities.User;
import com.CURD.curd_operation.repository.UserRepository;
import com.CURD.curd_operation.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponseDto registerUser(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponseDto(token, "User registration successful");
    }

    public AuthResponseDto loginUser(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found after authentication"));

        String token = jwtService.generateToken(user);
        return new AuthResponseDto(token, "User login successful");
    }
}
