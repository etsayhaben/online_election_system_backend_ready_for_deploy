package com.onlineelection.system.UserModelService.DTO;

public class RegisterAccountDTO {
    private String studentId;  // Use camel case for consistency
    private String fullName;
    private String email;
    private String role;
    private String password;

    public RegisterAccountDTO() {} // No-argument constructor

    public RegisterAccountDTO(String studentId, String fullName, String email, String role, String password) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
}
