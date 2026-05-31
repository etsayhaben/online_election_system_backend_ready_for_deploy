package com.onlineelection.system.RegisterationService.DTO;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotBlank(message = "Password is required")
    private String password;

    // Optional: Remove email if not used for login; keep it only if necessary
    private String email;

    // Constructor with all fields
    public LoginRequest(String studentId, String password, String email) {
        this.studentId = studentId;
        this.password = password;
        this.email = email;
    }

    // Default constructor for deserialization
    public LoginRequest() {}

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
