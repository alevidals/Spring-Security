package com.security.controller;

import com.security.dto.ChangePasswordDto;
import com.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/reset-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordDto changePasswordDto,
            Principal connectedUser
    ) {
        System.out.println(connectedUser);
        userService.changePassword(changePasswordDto, connectedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
