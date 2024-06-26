package com.security;

import com.security.model.Role;
import com.security.model.User;
import com.security.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecurityApplication {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityApplication(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            User adminUser = User.builder()
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.ADMIN)
                    .build();

            User normalUser = User.builder()
                    .email("test@test.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.USER)
                    .build();

            User savedAdminUser = userService.save(adminUser);
            System.out.println("Admin user saved! " + savedAdminUser.getEmail());

            User savedNormalUser = userService.save(normalUser);
            System.out.println("Normal user saved! " + savedNormalUser.getEmail());
        };
    }
}
