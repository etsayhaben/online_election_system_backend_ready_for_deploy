package com.onlineelection.system.AUTHORIZATION;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;

import com.onlineelection.system.UserModelService.Entity.Account;
import com.onlineelection.system.UserModelService.Repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AccountRepository accountRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors() // Ensure CORS is applied
            .and()
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permit all requests
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return studentId -> {
            Optional<Account> accountOptional = accountRepository.findByStudentId(studentId);
            Account account = accountOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return User.withUsername(account.getStudentId()) // Use studentId for username
                       .password(account.getPassword())
                       .roles(account.getRole()) // Ensure this returns the correct role
                       .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
