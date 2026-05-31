package com.onlineelection.system.RegisterationService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.onlineelection.system.RegisterationService.Entity.ElectionComNominees;
import com.onlineelection.system.RegisterationService.Service.ElectionComNomineesService;
import com.onlineelection.system.UserModelService.Entity.Account;
import com.onlineelection.system.UserModelService.DTO.RegisterAccountDTO;
import com.onlineelection.system.RegisterationService.Service.VoterService;
import com.onlineelection.system.UserModelService.Service.AccountService;

@RestController
@RequestMapping("/electioncom")
public class ElectionComController {

    @Autowired
    private ElectionComNomineesService committeeRegistrationService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private VoterService voterService;

    @PostMapping("/nominees")
    public ResponseEntity<String> registerNominee(
        @RequestParam("firstName") String firstName,
        @RequestParam("lastName") String lastName,
        @RequestParam(value = "yearOfStudy", required = false) String yearOfStudy,
        @RequestParam(value = "studentId", required = false) String studentId,
        @RequestParam("phoneNumber") String phoneNumber,
        @RequestParam("email") String email,
        @RequestParam("committeeDescription") String committeeDescription
) {

    try {
        ElectionComNominees committeeRegistration = new ElectionComNominees();

        boolean isExistingAccount = accountService.checkExistingStudentId(studentId);

        if (studentId != null && !studentId.isBlank() && isExistingAccount) {
            boolean isValidStudent = voterService.checkExistingStudentId(studentId);

            if (!isValidStudent) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Student ID does not exist.");
            }
        }

        committeeRegistration.setFirstName(firstName);
        committeeRegistration.setLastName(lastName);
        committeeRegistration.setYearOfStudy(yearOfStudy);
        committeeRegistration.setStudentId(studentId);
        committeeRegistration.setPhoneNumber(phoneNumber);
        committeeRegistration.setCommitteeDescription(committeeDescription);

        RegisterAccountDTO account = new RegisterAccountDTO();
        account.setStudentId(studentId);
        account.setRole("ELECTIONCOMMITTEE");
        account.setPassword(firstName);
        account.setEmail(email);

        accountService.saveAccount(account);
        committeeRegistrationService.saveCommitteeRegistration(committeeRegistration);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Registration successful!");

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Registration failed: " + e.getMessage());
    }
}
}