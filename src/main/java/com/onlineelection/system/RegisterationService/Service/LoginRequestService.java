package com.onlineelection.system.RegisterationService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Import for BCrypt
import org.springframework.stereotype.Service;
import com.onlineelection.system.UserModelService.Entity.Account;
import com.onlineelection.system.UserModelService.Repository.AccountRepository;
import com.onlineelection.system.exception.UserNotFoundException;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class LoginRequestService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Add BCryptPasswordEncoder

    public Account checkUserAvailability(String studentId, String password) {
        // Fetch account by studentId
        Optional<Account> accountOptional = accountRepository.findByStudentId(studentId);
        
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            
            // Use passwordEncoder.matches() to compare plain password with hashed password
            if (account.getPassword() != null || passwordEncoder.matches(password, account.getPassword())) {
                // Return the account if password matches
                return account;
            } else {
                // Logging for debugging purposes, avoid logging passwords
                System.out.println("Password mismatch for studentId: " + studentId);
            }
        }
        
        // Return null if user not found or password mismatch
        return null; // Return null instead of throwing an exception
    }
    
    
}
