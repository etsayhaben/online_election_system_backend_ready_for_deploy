package com.onlineelection.system.UserModelService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineelection.system.RegisterationService.Entity.Campaign; 
import com.onlineelection.system.RegisterationService.Entity.MemberOfParliament;
import com.onlineelection.system.RegisterationService.Repository.CampaignRepository;
import com.onlineelection.system.RegisterationService.Repository.MemberOfParlamentRepository; // Corrected Repository name
import com.onlineelection.system.UserModelService.DTO.RegisterAccountDTO;
import com.onlineelection.system.UserModelService.Entity.Account;
import com.onlineelection.system.UserModelService.Repository.AccountRepository;
import com.onlineelection.system.exception.UserNotFoundException;

import jakarta.mail.MessagingException;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CampaignRepository campaignRepository;
    private final MemberOfParlamentRepository memberOfParliamentRepository; // Corrected Repository name
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;

    @Autowired
    public AccountService(MemberOfParlamentRepository memberOfParliamentRepository, 
                          AccountRepository accountRepository,
                          CampaignRepository campaignRepository, 
                          PasswordEncoder passwordEncoder,
                          VerificationCodeService verificationCodeService) {
        this.accountRepository = accountRepository;
        this.memberOfParliamentRepository = memberOfParliamentRepository; // Initialize corrected repository
        this.campaignRepository = campaignRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationCodeService = verificationCodeService;
    }


  


    @Transactional
    public Account saveAccount(RegisterAccountDTO registerAccountDTO) throws Exception {
        // Validate DTO
        if (registerAccountDTO == null) {
            throw new IllegalArgumentException("RegisterAccountDTO cannot be null");
        }

        Account account = new Account();
        account.setStudentId(registerAccountDTO.getStudentId());
        account.setEmail(registerAccountDTO.getEmail());
        String password=registerAccountDTO.getPassword();
        // Set role and password based on the provided DTO
        String role = registerAccountDTO.getRole();
        
        if (role != null) {
            account.setRole("ROLE_" + role.toUpperCase());
        } else {
            account.setRole("ROLE_VOTER"); // Default role
        }

        // Password handling
        if (password != null) {
            account.setPassword(passwordEncoder.encode(password));
        } else {
            String randomPassword = generateRandomPassword();
            account.setPassword(passwordEncoder.encode(randomPassword));
            sendDefaultPasswordEmail(registerAccountDTO.getEmail(), randomPassword);
        }

        Account savedAccount = accountRepository.save(account);
        createMemberOfParliamentIfNeeded(savedAccount, account.getRole());

        return savedAccount;
    }

    private void sendDefaultPasswordEmail(String email, String randomPassword) {
        try {
            verificationCodeService.sendDefaultPasswordEmail(email, randomPassword);
            // Use a logging framework here instead of System.out.println
        } catch (MessagingException e) {
            // Handle exception, possibly throw a custom exception
        }
    }

    private void createMemberOfParliamentIfNeeded(Account savedAccount, String role) {
        if ("ROLE_MEMBEROFPARLIAMENT".equals(role)) {
            MemberOfParliament mp = new MemberOfParliament();
            mp.setIsCandidate(false);
            mp.setIsPasswordChanged(false);
            mp.setAccount(savedAccount);
            memberOfParliamentRepository.save(mp);
        }
    }

    public Optional<Account> getAccountByStudentId(String studentId) {
        return accountRepository.findByStudentId(studentId);
    }

    public boolean checkExistingStudentId(String studentId) {
        return accountRepository.findByStudentId(studentId).isPresent();
    }

    public boolean checkExistingEmail(String email) {
        return accountRepository.findByEmail(email) != null;
    }

    public String getPasswordByStudentId(String studentId) {
        return accountRepository.findByStudentId(studentId)
            .map(Account::getPassword)
            .orElseThrow(() -> new UserNotFoundException("User not found with studentId: " + studentId));
    }

    public String getRoleByStudentId(String studentId) {
        return accountRepository.findByStudentId(studentId)
            .map(Account::getRole)
            .orElseThrow(() -> new UserNotFoundException("User not found with studentId: " + studentId));
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }

        return password.toString();
    }



    @Transactional
public void updatePassword(String studentId, String newPassword) {
    Account account = accountRepository.findByStudentId(studentId)
        .orElseThrow(() -> new UserNotFoundException("User not found with studentId: " + studentId));

    System.out.println("Updating password for studentId: " + studentId);
    System.out.println("the  password for studentId: " + newPassword);
    String encodedPassword = passwordEncoder.encode(newPassword);
    account.setPassword(encodedPassword);
    accountRepository.save(account);

    MemberOfParliament memberOfParliament = memberOfParliamentRepository.findByAccount(account);
    if (memberOfParliament != null) {
        System.out.println("Updating password status for member of parliament: " + account.getStudentId());
        memberOfParliament.setIsPasswordChanged(true);
        memberOfParliamentRepository.save(memberOfParliament);
    } else {
        System.out.println("No member of parliament found for studentId: " + studentId);
    }
}
   public boolean isPasswordChanged(String studentId) {
        MemberOfParliament mp = memberOfParliamentRepository.findByAccountStudentId(studentId);
        System.out.println("get password changed is"+mp.getIsPasswordChanged());
        if (mp != null) {
            return mp.getIsPasswordChanged();
        }
        throw new IllegalArgumentException("No Member of Parliament found with studentId: " + studentId);
    }

    public void updateRole(Long campaignId) {
        Campaign campaign = campaignRepository.findByCampaignId(campaignId);
        if (campaign != null) {
            MemberOfParliament mop = campaign.getMemberOfParliament();
            if (mop != null) {
                Account account = mop.getAccount();
                if (account != null) {
                    account.setRole("ROLE_CANDIDATE");
                    accountRepository.save(account);
                }
            }
        }
    }
}
