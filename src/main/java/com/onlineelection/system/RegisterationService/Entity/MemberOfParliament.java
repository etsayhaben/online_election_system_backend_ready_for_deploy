package com.onlineelection.system.RegisterationService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import com.onlineelection.system.UserModelService.Entity.Account;

@Entity
public class MemberOfParliament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isCandidate;
    private Boolean isPasswordChanged; // New field

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "studentId")
    private Account account;

    @OneToOne
    @JoinColumn(name = "voter_id", referencedColumnName = "id") // Assuming you have a reference to the Voter
    private Voter voter;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsCandidate() {
        return isCandidate;
    }

    public void setIsCandidate(Boolean isCandidate) {
        this.isCandidate = isCandidate;
    }

    public Boolean getIsPasswordChanged() {
        return isPasswordChanged;
    }

    public void setIsPasswordChanged(Boolean isPasswordChanged) {
        this.isPasswordChanged = isPasswordChanged;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Voter getVoter() {
        return voter;
    }

    public void setVoter(Voter voter) {
        this.voter = voter;
    }

    // Method to get the associated student ID
    public String getStudentId() {
        return account != null ? account.getStudentId() : null; // Return student ID from the associated account
    }
}
