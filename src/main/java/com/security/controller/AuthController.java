package com.security.controller;

import com.security.dto.AuthenticationDto;
import com.security.dto.LoginDto;
import com.security.dto.RegisterDto;
import com.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationDto> register(@RequestBody RegisterDto registerDto) {
        AuthenticationDto authenticationDto = authService.register(registerDto);
        return new ResponseEntity<>(authenticationDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(@RequestBody LoginDto loginDto) {
        AuthenticationDto authenticationDto = authService.login(loginDto);
        return new ResponseEntity<>(authenticationDto, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationDto> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthenticationDto authenticationDto = authService.refreshToken(request, response);
        return new ResponseEntity<>(authenticationDto, HttpStatus.OK);
    }

}
