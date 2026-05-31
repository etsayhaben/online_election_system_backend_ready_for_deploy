package com.onlineelection.system.UserModelService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import com.onlineelection.system.RegisterationService.Entity.MemberOfParliament;

@Entity
public class Account {

    @Id
    private String studentId;  // This field remains here in Account
    private String email;
    private String password;
    private String role;

    // @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private MemberOfParliament memberOfParliament;

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // public MemberOfParliament getMemberOfParliament() {
    //     return memberOfParliament;
    // }

    // public void setMemberOfParliament(MemberOfParliament memberOfParliament) {
    //     this.memberOfParliament = memberOfParliament;
    // }
}
