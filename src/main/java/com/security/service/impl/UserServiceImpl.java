package com.security.service.impl;

import com.security.dto.ChangePasswordDto;
import com.security.exception.BadRequestException;
import com.security.exception.UserNotFoundException;
import com.security.model.User;
import com.security.repository.UserRepository;
import com.security.service.UserService;
import com.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email " + email));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findOne(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto, Principal connectedUser) {
        User currentUser = SecurityUtils.getCurrentUser(connectedUser);

        if (!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), currentUser.getPassword())) {
            throw new BadRequestException("The current password do not match with the current one");
        }

        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmationPassword())) {
            throw new BadRequestException("The password and confirmation password are not the same");
        }

        currentUser.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(currentUser);
    }
}
