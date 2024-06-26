package com.security.service.impl;

import com.security.dto.AuthenticationDto;
import com.security.dto.LoginDto;
import com.security.dto.RegisterDto;
import com.security.exception.RequiredHeaderNotFoundException;
import com.security.exception.TokenNotValidException;
import com.security.model.Role;
import com.security.model.Token;
import com.security.model.TokenType;
import com.security.model.User;
import com.security.service.AuthService;
import com.security.service.JwtService;
import com.security.service.TokenService;
import com.security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final TokenService tokenService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationDto register(RegisterDto registerDto) {
        User user = User.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userService.save(user);
        String jwtToken = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(jwtToken, savedUser);

        return AuthenticationDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationDto login(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        User user = userService.findByEmail(loginDto.getEmail());
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokenService.revokeAllUserTokens(user.getId());
        saveUserToken(jwtToken, user);

        return AuthenticationDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(String jwtToken, User user) {
        Token token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .user(user)
                .build();

        tokenService.save(token);
    }

    @Override
    public AuthenticationDto refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")) {
            throw new RequiredHeaderNotFoundException("The authorization header or Bearer prefix is not present");
        }

        final String refreshToken = authenticationHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new TokenNotValidException("The token provided is not valid");
        }

        User user = userService.findByEmail(userEmail);

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new TokenNotValidException("The token provided is not valid");
        }

        String accessToken = jwtService.generateToken(user);
        tokenService.revokeAllUserTokens(user.getId());
        saveUserToken(accessToken, user);

        return AuthenticationDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
