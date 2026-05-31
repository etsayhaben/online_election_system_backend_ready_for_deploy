package com.onlineelection.system.UserModelService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.onlineelection.system.UserModelService.DTO.RegisterAccountDTO;
import org.springframework.beans.factory.annotation.Value;
@Component
public class AdminAccountInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AdminAccountInitializer.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder; // Make sure BCryptPasswordEncoder is being used

    @Value("${admin.email}")
    private String email;

    @Value("${admin.studentId}")
    private String studentId;

    @Value("${admin.password}")
    private String password; // Plaintext password from properties file

    @Override
    public void run(String... args) throws Exception {
        createAdminAccount();
    }

    private void createAdminAccount() {
        // Hash the plaintext password here, instead of using already hashed password
        String hashedPassword = passwordEncoder.encode(password);

        RegisterAccountDTO adminAccount = new RegisterAccountDTO();
        adminAccount.setEmail(email);
        adminAccount.setStudentId(studentId);
        adminAccount.setPassword(hashedPassword); // Set the hashed password
        adminAccount.setRole("ADMIN");

        // Check if the admin account already exists
        if (!accountService.checkExistingStudentId(studentId)) {
            try {
                accountService.saveAccount(adminAccount);
                logger.info("Admin account created: " + email);
            } catch (Exception e) {
                logger.error("Error creating admin account", e);
            }
        } else {
            logger.info("Admin account already exists: " + email);
        }
    }
}
