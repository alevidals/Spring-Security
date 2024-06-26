package com.security.service;

import com.security.dto.AuthenticationDto;
import com.security.dto.LoginDto;
import com.security.dto.RegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {

    AuthenticationDto register(RegisterDto registerDto);

    AuthenticationDto login(LoginDto loginDto);

    AuthenticationDto refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
