package com.security.service;

import com.security.model.Token;

public interface TokenService {
    void save(Token token);

    void revokeAllUserTokens(String id);
}
