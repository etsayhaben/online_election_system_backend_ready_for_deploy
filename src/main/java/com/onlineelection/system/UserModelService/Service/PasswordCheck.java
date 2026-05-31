package com.onlineelection.system.UserModelService.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCheck {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String plainPassword = "12345678"; // Password you are trying to verify
        String hashedPassword = "$2a$10$AifIjpu93B2030TORHLsluk2K65D8Tkc0VuLcIUJk5DepXg74vCk."; // Hashed password from the database

        boolean matches = encoder.matches(plainPassword, hashedPassword);
        System.out.println("Password matches: " + matches); // Should print true if it matches
    }
}
