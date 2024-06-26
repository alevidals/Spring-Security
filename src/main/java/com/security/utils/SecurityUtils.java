package com.security.utils;

import com.security.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

public final class SecurityUtils {

    public static User getCurrentUser(Principal connectedUser) {
        return (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
    }

}
