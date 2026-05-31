package com.onlineelection.system.RegisterationService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.onlineelection.system.UserModelService.DTO.*;
import com.onlineelection.system.RegisterationService.DTO.*;
import com.onlineelection.system.RegisterationService.Service.*;
import com.onlineelection.system.UserModelService.Entity.Account;
import com.onlineelection.system.UserModelService.Service.AccountService;
import com.onlineelection.system.UserModelService.Service.VerificationCodeService;
import com.onlineelection.system.exception.UserNotFoundException;
import com.onlineelection.system.utils.JwtUtil;

@RestController
@RequestMapping("/api/login")
public class LoginRequestController {

    @Autowired
    private LoginRequestService loginRequestService;

    @Autowired 
    private VoterService voterService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtUtil jwtUtil; // Inject JwtUtil

    @PostMapping("/checking")
    public ResponseEntity<Map<String, String>> checkForLogging(@RequestBody LoginRequest loginRequest) {
        String studentId = loginRequest.getStudentId();
        String password = loginRequest.getPassword();
    
        // Call the service method to validate user credentials
        Account account = loginRequestService.checkUserAvailability(studentId, password);
    
        // Check if account is null (user not found or password mismatch)
        if (account == null) {
            // Return a response indicating the error without revealing sensitive details
            return ResponseEntity.badRequest().body(Map.of("error", "Incorrect username or password"));
        }
    
        // If account exists and password matches, proceed to generate JWT token
        String role = account.getRole(); // Get the user's role
        System.out.println(role);
        
        // Generate JWT token with the role included
        String token = jwtUtil.generateToken(studentId, role);
    
        // Prepare response body
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Login successful");
        responseBody.put("role", role); // Add the role to the response body
        responseBody.put("token", token); // Add the JWT token to the response body
    
        // Return success with JSON data
        return ResponseEntity.ok(responseBody);
    }
    


    // Check if the password has been changed
    @GetMapping("/check-password-changed/{studentId}")
    public ResponseEntity<Boolean> isPasswordChanged(@PathVariable String studentId) {
        boolean isChanged = accountService.isPasswordChanged(studentId);
        return ResponseEntity.ok(isChanged);
    }

    // Verification of confirmation code
    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyConfirmationCode(@RequestBody Confirmation confirmationDto) {
        String email = confirmationDto.getEmail(); 
        int code = confirmationDto.getConfirmationCode(); 

        boolean isValid = verificationCodeService.isValidVerificationCode(email, code);
        if (isValid) {
            return ResponseEntity.ok(Map.of(
                "email", email,
                "studentId", confirmationDto.getStudentId() // Include studentId in response
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid confirmation code."));
        }
    }

    // Check account credentials and handle verification code logic
    @PostMapping("/check-credentials")
    public ResponseEntity<String> checkAccountCredentials(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String studentId = loginRequest.getStudentId();
        String password = loginRequest.getPassword(); 

        boolean isExistingEmailInVoter = voterService.checkExistingEmail(email);
        boolean isExistingIdInVoter = voterService.checkExistingStudentId(studentId);
        boolean isExistingAccount = accountService.checkExistingStudentId(studentId);
        
        if (isExistingAccount) {
            String storedPassword = accountService.getPasswordByStudentId(studentId);
            if (storedPassword != null && password.equals(storedPassword)) {
                String role = accountService.getRoleByStudentId(studentId);
                return ResponseEntity.ok("Login successful. Your role is: " + role);
            } else {
                return ResponseEntity.badRequest().body("Invalid password.");
            }
        }

        if (isExistingEmailInVoter && isExistingIdInVoter && !isExistingAccount) {
            try {
                sendVerificationCode(email); // Send verification code
            } catch (MailException ex) {
                ex.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Unable to send verification email. Check SMTP username/password or use a valid Gmail app password.");
            }
            return ResponseEntity.ok("Verification code sent to your email.");
        }

        return ResponseEntity.badRequest().body("Invalid ID or email. Please check your details.");
    }

    // Helper method to send verification code
    private void sendVerificationCode(String email) {
        Random random = new Random();
        int verificationCode = 100000 + random.nextInt(900000);  // Generate 6-digit code

        verificationCodeService.saveVerificationCode(email, verificationCode);

        // Send verification code email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Verification Code");
        message.setText("Your verification code is: " + verificationCode + "\nPlease enter this code to verify your account.");

        mailSender.send(message);
        System.out.println("Verification email sent to " + email + " with code: " + verificationCode);
    }
}
