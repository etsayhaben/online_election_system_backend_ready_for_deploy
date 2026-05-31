package com.onlineelection.system.UserModelService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException; // Import for MessagingException
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationCodeService {

    // Autowire the JavaMailSender
    @Autowired
    private JavaMailSender mailSender;

    // Store verification codes with corresponding emails
    private final Map<String, Integer> verificationCodeMap = new HashMap<>();

    // Generate and send the verification code
    public void sendVerificationCode(String email) {
        Random random = new Random();
        // Generates a 6-digit number
        int verificationCode = 100000 + random.nextInt(900000); 

        // Save the code with the email
        verificationCodeMap.put(email, verificationCode);

        // Logic to send the email goes here (this is just a placeholder for now)
        System.out.println("Verification code sent to " + email + ": " + verificationCode);
    }

    // Method to save the verification code for external use
    public void saveVerificationCode(String email, int verificationCode) {
        verificationCodeMap.put(email, verificationCode);
    }

    // Validate the verification code
    public boolean isValidVerificationCode(String email, int code) {
        // Check if the email exists in the map and if the code matches the stored code
        if (verificationCodeMap.containsKey(email) && verificationCodeMap.get(email) == code) {
            // Optionally, remove the verification code once it has been successfully validated
            verificationCodeMap.remove(email);
            return true;
        } else {
            return false;
        }
    }

    // Method to send the default password via email
    public void sendDefaultPasswordEmail(String email, String defaultPassword) throws MessagingException {
        // Create and send the email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Default Password");
        message.setText("Dear User,\n\n" +
                         "Your account has been created successfully.\n" +
                         "Your default password is: " + defaultPassword + "\n" +
                         "Please change it after your first login.\n\n" +
                         "Best Regards,\nOnline Election System Team");

        mailSender.send(message);

        System.out.println("Default password email sent to " + email + " with password: " + defaultPassword);
    }
}
