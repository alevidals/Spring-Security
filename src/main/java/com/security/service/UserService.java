package com.security.service;

import com.security.dto.ChangePasswordDto;
import com.security.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    User save(User user);

    User findByEmail(String email);

    List<User> findAll();

    User findOne(String id);

    void changePassword(ChangePasswordDto changePasswordDto, Principal connectedUser);
    
}
