package com.onlineelection.system.UserModelService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize; // Import for role-based access
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Optional;
import com.onlineelection.system.RegisterationService.Entity.Voter;

import java.util.List;
import com.onlineelection.system.UserModelService.DTO.RegisterAccountDTO;
import com.onlineelection.system.UserModelService.Entity.Account;
import com.onlineelection.system.UserModelService.Service.AccountService;
import com.onlineelection.system.UserModelService.Service.VerificationCodeService;
import com.onlineelection.system.exception.UserNotFoundException;
import com.onlineelection.system.RegisterationService.Service.VoterService;
import com.onlineelection.system.UserModelService.DTO.Confirmation;

@RestController
@RequestMapping("/admin/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private VoterService voterService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private JavaMailSender mailSender;

    @CrossOrigin(origins = "http://localhost:3000")
@PostMapping("/create")
public ResponseEntity<String> createAccount(@RequestBody RegisterAccountDTO registerAccountDTO) throws Exception {
    String studentId = registerAccountDTO.getStudentId();
    String role = registerAccountDTO.getRole();

    // Check if the account already exists
    boolean isExistingAccount = accountService.checkExistingStudentId(studentId);
    if (isExistingAccount) {
        return ResponseEntity.badRequest().body("Username taken");
    }

    // Check if the ID exists in the voter repository
    Voter student = voterService.getDataFromVoter(studentId);
    if (student == null) {
        return ResponseEntity.badRequest().body("Invalid ID. Please register in the voter system first.");
    }

    // Set email from the found voter and ensure role is properly assigned
    registerAccountDTO.setEmail(student.getEmail());

    // Save the account
    accountService.saveAccount(registerAccountDTO);

    return ResponseEntity.ok("Created successfully");
}

 @PostMapping("/change-password")
public ResponseEntity<String> changePassword(@RequestBody RegisterAccountDTO changePasswordDTO) {
    try {
        System.out.print("the password to be changed is"+changePasswordDTO.getPassword());
        accountService.updatePassword(changePasswordDTO.getStudentId(), changePasswordDTO.getPassword());
        return ResponseEntity.ok("Password changed successfully.");
    } catch (UserNotFoundException e) {
        return ResponseEntity.status(404).body("User not found.");
    } catch (Exception e) {
        e.printStackTrace();  // Log the error details
        return ResponseEntity.status(500).body("An internal error occurred: " + e.getMessage());
    }
}
  @GetMapping("/check-password-changed")
public ResponseEntity<Boolean> isPasswordChanged(@RequestParam String studentId) {
    boolean isChanged = accountService.isPasswordChanged(studentId);
    return ResponseEntity.ok(isChanged);
}






    @PreAuthorize("hasRole('ADMIN')") // Restrict access to ADMIN only
    @GetMapping("/{studentId}")
    public ResponseEntity<Account> getAccountDetails(@PathVariable String studentId) {
        Optional<Account> accountOptional = accountService.getAccountByStudentId(studentId);
        if (accountOptional.isPresent()) {
            return ResponseEntity.ok(accountOptional.get()); // Return the account if present
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }
}
