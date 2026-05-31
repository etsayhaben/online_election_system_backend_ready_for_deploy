package com.onlineelection.system.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends a verification email with a token link to the user.
     * 
     * @param recipientEmail The email address to send the verification to.
     * @param token The verification token to be sent.
     * @throws MessagingException If there is an issue sending the email.
     */
    public void sendVerificationEmail(String recipientEmail, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String subject = "Email Verification";
        String confirmationUrl = "http://localhost:8080/verify?token=" + token;
        String htmlContent = "<h1>Please verify your email</h1>" +
                "<p>Click the link below to verify your email:</p>" +
                "<a href='" + confirmationUrl + "'>Verify your email</a>";

        helper.setFrom("no-reply@onlineelection.com");
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message); // Send the email
    }
}
