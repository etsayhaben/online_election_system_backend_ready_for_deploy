package com.onlineelection.system.EmailService;

import org.apache.catalina.connector.Response;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.onlineelection.system.RegisterationService.Controller.LoginRequestController;
import com.onlineelection.system.RegisterationService.DTO.LoginRequest;
import com.onlineelection.system.RegisterationService.Service.CandidateService;
import com.onlineelection.system.RegisterationService.Service.LoginRequestService;
import com.onlineelection.system.UserModelService.Entity.Account;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;

@Component
public class EmailServiceRunner implements CommandLineRunner //
{

    private final EmailService emailService;
    private final LoginRequestService loginRequestService;
    private final LoginRequestController loginRequestController;
    private final CandidateService candidateService;

    public EmailServiceRunner(CandidateService candidateService, EmailService emailService,
            LoginRequestService loginRequestService,
            LoginRequestController loginRequestController) {
        this.loginRequestService = loginRequestService;
        this.candidateService = candidateService;
        this.emailService = emailService;
        this.loginRequestController = loginRequestController;
    }

    @Override
    public void run(String... args) {
        // ResponseEntity<?> isAvailable =
        // loginRequestController.checkUserAvailability("haben", "etsay");

        // if (isAvailable != null) {
        // System.out.println(isAvailable);
        // System.out.println(candidateService.getCandidates());
        // }

        // else
        // System.out.println("user is not found");

    }

    // @Override
    // public void run(String... args) {
    // try {
    // emailService.sendHtmlEmail();
    // System.out.println("Email sent successfully!");
    // } catch (MessagingException e) {
    // System.err.println("Failed to send email: " + e.getMessage());
    // }
    // }

}
