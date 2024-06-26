package com.security.service.impl;

import com.security.model.Token;
import com.security.repository.TokenRepository;
import com.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public void save(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(String id) {
        List<Token> validTokens = tokenRepository.findAllValidTokensByUser(id);
        if (validTokens.isEmpty()) return;

        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validTokens);
    }
}
